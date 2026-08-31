package com.stockroom.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stockroom.data.AppUserRepository;
import com.stockroom.domain.AppUser;

/**
 * Spring Security asks: "who is this username?" We look them up in our table
 * and hand back a UserDetails object (username, hashed password, role).
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

	private final AppUserRepository users;

	public AppUserDetailsService(AppUserRepository users) {
		this.users = users;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		AppUser user = users.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("No user: " + username));

		return User.withUsername(user.getUsername())
				.password(user.getPassword())
				.roles(user.getRole().name())
				.disabled(!user.isEnabled())
				.build();
	}
}
