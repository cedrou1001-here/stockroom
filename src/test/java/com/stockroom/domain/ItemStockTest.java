package com.stockroom.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ItemStockTest {

	private Item laptop() {
		Item item = new Item();
		item.setName("Laptop");
		item.setSku("IT-1");
		item.setCategory("Electronics");
		item.setQuantityOnHand(5);
		item.setActive(true);
		return item;
	}

	@Test
	void checkoutReducesQuantity() {
		Item item = laptop();
		item.removeFromShelf(2);
		assertEquals(3, item.getQuantityOnHand());
	}

	@Test
	void cannotCheckoutMoreThanOnShelf() {
		Item item = laptop();
		assertThrows(InsufficientStockException.class, () -> item.removeFromShelf(9));
		assertEquals(5, item.getQuantityOnHand());
	}

	@Test
	void cannotCheckoutInactiveItem() {
		Item item = laptop();
		item.setActive(false);
		assertThrows(ItemNotAvailableException.class, () -> item.removeFromShelf(1));
	}

	@Test
	void returnPutsStockBack() {
		Item item = laptop();
		item.removeFromShelf(2);
		item.returnToShelf(2);
		assertEquals(5, item.getQuantityOnHand());
	}
}
