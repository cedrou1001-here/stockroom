package com.stockroom.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.stockroom.data.CheckoutRepository;
import com.stockroom.data.ItemRepository;
import com.stockroom.domain.Checkout;
import com.stockroom.domain.Item;

/**
 * Heart of StockRoom: checking out decreases stock; returning increases it.
 * Both happen in one database transaction so they cannot get out of sync.
 */
@Service
public class CheckoutService {

	private final CheckoutRepository checkouts;
	private final ItemRepository items;

	public CheckoutService(CheckoutRepository checkouts, ItemRepository items) {
		this.checkouts = checkouts;
		this.items = items;
	}

	public List<Checkout> findOpen() {
		return checkouts.findOpenWithItem();
	}

	public DashboardStats dashboard() {
		LocalDate today = LocalDate.now();
		return new DashboardStats(checkouts.countByReturnedAtIsNull(), checkouts.countOverdue(today), items.count());
	}

	@Transactional
	public Checkout checkout(Long itemId, String borrowerName, String borrowerEmail, int quantity, LocalDate dueDate,
			String staffUsername) {
		Item item = items.findById(itemId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
		item.removeFromShelf(quantity);
		Checkout loan = new Checkout(item, borrowerName, borrowerEmail, quantity, dueDate, staffUsername);
		return checkouts.save(loan);
	}

	@Transactional
	public void returnCheckout(Long checkoutId) {
		Checkout loan = checkouts.findById(checkoutId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Checkout not found"));
		loan.markReturned();
	}

	public record DashboardStats(long openCheckouts, long overdue, long itemCount) {
	}
}
