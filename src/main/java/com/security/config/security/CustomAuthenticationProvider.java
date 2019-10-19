package com.security.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.security.api.domain.UserDetailsDTO;
import com.security.api.service.UserService;
import com.security.config.security.paramExtra.ExtraParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String idAuth = authentication.getName();
		String pwdAuth = (String) authentication.getCredentials();

		String custom_param = ((ExtraParam) authentication.getDetails()).getCustom_parameter();
		UserDetailsDTO user = null;
		try {
			user = userService.readUser(idAuth);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

		return new UsernamePasswordAuthenticationToken(user, pwdAuth, user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}
}
