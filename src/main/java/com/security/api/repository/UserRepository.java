package com.security.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.api.domain.User;

public interface UserRepository extends JpaRepository<User, String>{
}
