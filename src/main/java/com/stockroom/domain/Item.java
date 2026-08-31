package com.stockroom.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "item")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Name is required")
	@Size(max = 120)
	@Column(nullable = false, length = 120)
	private String name;

	@NotBlank(message = "SKU is required")
	@Size(max = 40)
	@Column(nullable = false, unique = true, length = 40)
	private String sku;

	@NotBlank(message = "Category is required")
	@Size(max = 60)
	@Column(nullable = false, length = 60)
	private String category;

	@NotNull
	@Min(value = 0, message = "Quantity cannot be negative")
	@Column(name = "quantity_on_hand", nullable = false)
	private Integer quantityOnHand = 0;

	@Column(nullable = false)
	private boolean active = true;

	public Item() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getQuantityOnHand() {
		return quantityOnHand;
	}

	public void setQuantityOnHand(Integer quantityOnHand) {
		this.quantityOnHand = quantityOnHand;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Take units off the shelf. Fails if the item is inactive or there is not enough stock.
	 */
	public void removeFromShelf(int quantity) {
		if (!active) {
			throw new ItemNotAvailableException("This item is inactive.");
		}
		if (quantity < 1) {
			throw new InsufficientStockException("Check out at least 1.");
		}
		if (quantityOnHand < quantity) {
			throw new InsufficientStockException(
					"Only " + quantityOnHand + " on the shelf, cannot check out " + quantity + ".");
		}
		quantityOnHand -= quantity;
	}

	public void returnToShelf(int quantity) {
		if (quantity < 1) {
			throw new InsufficientStockException("Return at least 1.");
		}
		quantityOnHand += quantity;
	}
}
