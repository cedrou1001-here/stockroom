package com.stockroom.service;

/**
 * Thrown when someone tries to save an SKU that another item already has.
 */
public class DuplicateSkuException extends RuntimeException {

	public DuplicateSkuException(String sku) {
		super("SKU already in use: " + sku);
	}
}
