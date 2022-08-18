package com.microservice.oauth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.microservice.commons.user.entity.Users;
import com.microservice.oauth.service.IUUserService;

@Component
public class AdditionalInformationToken implements TokenEnhancer{

	@Autowired
	private IUUserService iuUserService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		Map<String, Object> info = new HashMap<String, Object>();
		
		Users users = iuUserService.findByUsername(authentication.getName());
		
		info.put("name", users.getName());
		info.put("email", users.getEmail());
		info.put("isActive", users.getIsActive());
		info.put("password", users.getPassword());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}
	
	

}
