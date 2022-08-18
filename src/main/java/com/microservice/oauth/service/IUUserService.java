package com.microservice.oauth.service;

import com.microservice.commons.user.entity.Users;

public interface IUUserService {
	
	public Users findByUsername(String username);

}
