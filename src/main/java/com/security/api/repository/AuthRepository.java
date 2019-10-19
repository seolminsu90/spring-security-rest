package com.security.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.api.domain.Auth;

public interface AuthRepository extends JpaRepository<Auth, String>{
	List<String> findRoleById(String id);
}
