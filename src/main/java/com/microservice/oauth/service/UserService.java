package com.microservice.oauth.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.microservice.commons.user.entity.Users;
import com.microservice.oauth.client.UserFeignClient;

@Service
public class UserService implements UserDetailsService, IUUserService {

	@Autowired
	private UserFeignClient client;

	private Logger log = LoggerFactory.getLogger(UserService.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Users users = client.findByUsername(username);

		if (users == null) {
			throw new UsernameNotFoundException("User not exist: " + username);
		}

		List<GrantedAuthority> authorities = users.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.peek(authority -> log.info("Role: " + authority.getAuthority())).collect(Collectors.toList());

		log.info("User authenticate: " + username);

		return new User(users.getName(), users.getPassword(), users.getIsActive(), true, true, true, authorities);
	}

	@Override
	public Users findByUsername(String username) {
		
		return client.findByUsername(username);
	}

}
