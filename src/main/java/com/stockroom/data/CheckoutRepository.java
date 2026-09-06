package com.stockroom.data;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.stockroom.domain.Checkout;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

	List<Checkout> findByReturnedAtIsNullOrderByDueDateAsc();

	@Query("""
			SELECT c FROM Checkout c
			JOIN FETCH c.item
			WHERE c.returnedAt IS NULL
			ORDER BY c.dueDate
			""")
	List<Checkout> findOpenWithItem();

	long countByReturnedAtIsNull();

	@Query("""
			SELECT COUNT(c) FROM Checkout c
			WHERE c.returnedAt IS NULL AND c.dueDate < :today
			""")
	long countOverdue(LocalDate today);
}
