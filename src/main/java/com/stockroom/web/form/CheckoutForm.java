package com.stockroom.web.form;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CheckoutForm {

	@NotNull(message = "Choose an item")
	private Long itemId;

	@NotBlank(message = "Borrower name is required")
	@Size(max = 120)
	private String borrowerName;

	@NotBlank(message = "Email is required")
	@Email(message = "Enter a valid email")
	@Size(max = 180)
	private String borrowerEmail;

	@NotNull
	@Min(value = 1, message = "Check out at least 1")
	private Integer quantity = 1;

	@NotNull(message = "Due date is required")
	@FutureOrPresent(message = "Due date cannot be in the past")
	private LocalDate dueDate = LocalDate.now().plusDays(7);

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getBorrowerEmail() {
		return borrowerEmail;
	}

	public void setBorrowerEmail(String borrowerEmail) {
		this.borrowerEmail = borrowerEmail;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
}
