package com.stockroom.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.stockroom.data.AppUserRepository;
import com.stockroom.domain.AppUser;
import com.stockroom.domain.Role;

/**
 * First boot with an empty user table: create the two demo accounts.
 * Passwords are hashed with BCrypt before they are stored. We never save
 * plain text passwords.
 */
@Component
public class DemoUserSeeder implements CommandLineRunner {

	private final AppUserRepository users;
	private final PasswordEncoder passwordEncoder;

	public DemoUserSeeder(AppUserRepository users, PasswordEncoder passwordEncoder) {
		this.users = users;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) {
		if (users.count() > 0) {
			return;
		}

		users.save(new AppUser("admin", passwordEncoder.encode("admin123"), Role.ADMIN));
		users.save(new AppUser("staff", passwordEncoder.encode("staff123"), Role.STAFF));
	}
}
