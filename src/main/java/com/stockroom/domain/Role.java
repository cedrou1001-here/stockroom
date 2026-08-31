package com.stockroom.domain;

/**
 * A role is a job title in the app, not a person.
 * ADMIN can manage inventory later. STAFF can check items in and out.
 */
public enum Role {
	ADMIN,
	STAFF
}
