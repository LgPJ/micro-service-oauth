package com.microservice.oauth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.microservice.commons.user.entity.Users;

@FeignClient(name = "service-user")
public interface UserFeignClient {
	
	@GetMapping("/user/search/find-user")
	public Users findByUsername(@RequestParam String username);

}
