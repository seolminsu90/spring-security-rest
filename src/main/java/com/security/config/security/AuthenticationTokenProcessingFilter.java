package com.security.config.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.security.api.domain.UserDetailsDTO;
import com.security.api.service.UserService;
import com.security.util.JWTUtil;

@Component
public class AuthenticationTokenProcessingFilter extends GenericFilterBean {
	@Autowired
	private UserService userService;

	@Autowired
	private JWTUtil jwtUtil;

	public AuthenticationTokenProcessingFilter(UserService userService, JWTUtil jwtUtil) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String authenticationStr = getAuthentication(httpRequest);
		String ipAdress = httpRequest.getRemoteAddr();

		if (authenticationStr != null) {
			Map<String, Object> resultMap = jwtUtil.checkToken(authenticationStr, ipAdress);

			int status = (Integer) resultMap.get("return");
			if (status == 0) {
				String id = (String) resultMap.get("id");
				List<String> auth_list = (List<String>) resultMap.get("auth_list");

				UserDetailsDTO user = new UserDetailsDTO();
				user.setAuthorities(setAuthorities(auth_list));
				user.setId(id);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
						user.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(request, response);
	}

	public Collection<GrantedAuthority> setAuthorities(List<String> auth_list) {
		List<GrantedAuthority> authorities = new ArrayList<>();

		auth_list.stream().forEach(authority -> {
			authorities.add(new SimpleGrantedAuthority(authority));
		});

		return authorities;
	}

	private String getAuthentication(HttpServletRequest httpRequest) {
		return httpRequest.getHeader("Authentication");
	}
}