package com.stockroom.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stockroom.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

	Optional<Item> findBySkuIgnoreCase(String sku);

	@Query("""
			SELECT i FROM Item i
			WHERE :q IS NULL OR :q = ''
			   OR LOWER(i.name) LIKE LOWER(CONCAT('%', :q, '%'))
			   OR LOWER(i.sku) LIKE LOWER(CONCAT('%', :q, '%'))
			   OR LOWER(i.category) LIKE LOWER(CONCAT('%', :q, '%'))
			ORDER BY i.name
			""")
	List<Item> search(@Param("q") String q);
}
