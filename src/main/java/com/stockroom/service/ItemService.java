package com.stockroom.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.stockroom.data.ItemRepository;
import com.stockroom.domain.Item;

/**
 * Business rules for items live here, not in the controller.
 * The controller talks HTTP; this class talks inventory.
 */
@Service
public class ItemService {

	private final ItemRepository items;

	public ItemService(ItemRepository items) {
		this.items = items;
	}

	public List<Item> search(String query) {
		return items.search(query == null ? "" : query.trim());
	}

	public Item get(Long id) {
		return items.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
	}

	@Transactional
	public Item create(Item item) {
		assertSkuAvailable(item.getSku(), null);
		return items.save(item);
	}

	@Transactional
	public Item update(Long id, Item incoming) {
		Item existing = get(id);
		assertSkuAvailable(incoming.getSku(), id);
		existing.setName(incoming.getName());
		existing.setSku(incoming.getSku());
		existing.setCategory(incoming.getCategory());
		existing.setQuantityOnHand(incoming.getQuantityOnHand());
		existing.setActive(incoming.isActive());
		return items.save(existing);
	}

	private void assertSkuAvailable(String sku, Long currentId) {
		items.findBySkuIgnoreCase(sku).ifPresent(match -> {
			if (currentId == null || !match.getId().equals(currentId)) {
				throw new DuplicateSkuException(sku);
			}
		});
	}
}
