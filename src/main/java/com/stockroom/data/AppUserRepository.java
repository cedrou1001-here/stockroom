package com.stockroom.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockroom.domain.AppUser;

/**
 * Spring Data writes the SQL for these methods from the method names.
 * findByUsername → SELECT * FROM app_user WHERE username = ?
 */
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

	Optional<AppUser> findByUsername(String username);
}
