package com.security.api.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.security.api.domain.User;
import com.security.api.domain.UserDetailsDTO;
import com.security.api.repository.AuthRepository;
import com.security.api.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthRepository authRepository;

	public UserDetailsDTO readUser(String id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			UserDetailsDTO userDto = new UserDetailsDTO();
			userDto.setAuthorities(getAuthorities(id));
			userDto.setId(id);
			return userDto;
		} else {
			return null;
		}
	}

	public Collection<GrantedAuthority> getAuthorities(String id) {
		// 실제 사용 할땐 [User] --< [Auth] 구조로 짜야 한다.
		List<String> authoritiesList = authRepository.findRoleById(id);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		authoritiesList.stream().forEach(authority -> {
			authorities.add(new SimpleGrantedAuthority(authority));
		});

		return authorities;
	}
}
