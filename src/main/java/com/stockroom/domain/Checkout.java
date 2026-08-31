package com.stockroom.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "checkout")
public class Checkout {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id", nullable = false)
	private Item item;

	@Column(name = "borrower_name", nullable = false, length = 120)
	private String borrowerName;

	@Column(name = "borrower_email", nullable = false, length = 180)
	private String borrowerEmail;

	@Column(nullable = false)
	private int quantity;

	@Column(name = "due_date", nullable = false)
	private LocalDate dueDate;

	@Column(name = "returned_at")
	private LocalDateTime returnedAt;

	@Column(name = "checked_out_by", nullable = false, length = 50)
	private String checkedOutBy;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	protected Checkout() {
	}

	public Checkout(Item item, String borrowerName, String borrowerEmail, int quantity, LocalDate dueDate,
			String checkedOutBy) {
		this.item = item;
		this.borrowerName = borrowerName;
		this.borrowerEmail = borrowerEmail;
		this.quantity = quantity;
		this.dueDate = dueDate;
		this.checkedOutBy = checkedOutBy;
		this.createdAt = LocalDateTime.now();
	}

	public boolean isOpen() {
		return returnedAt == null;
	}

	public boolean isOverdue() {
		return isOpen() && dueDate.isBefore(LocalDate.now());
	}

	public void markReturned() {
		if (returnedAt != null) {
			throw new IllegalStateException("This checkout is already returned.");
		}
		returnedAt = LocalDateTime.now();
		item.returnToShelf(quantity);
	}

	public Long getId() {
		return id;
	}

	public Item getItem() {
		return item;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public String getBorrowerEmail() {
		return borrowerEmail;
	}

	public int getQuantity() {
		return quantity;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public LocalDateTime getReturnedAt() {
		return returnedAt;
	}

	public String getCheckedOutBy() {
		return checkedOutBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
