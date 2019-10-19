package com.security.api.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	// 헥사 특정 로그인 security 제외하고
	// cookie 받아오고
	// header로 특정(아무) url 쏴보자
	// 인증 ok or no 되면 성공.
	// response 다듬으면 끝

	@Secured("ROLE_A")
	@GetMapping("/check/a")
	public String checkAuthA() {
		return "A is pass";
	}

	@Secured("ROLE_B")
	@GetMapping("/check/b")
	public String checkAuthB() {
		return "B is pass";
	}
	
	@GetMapping("/check/c")
	public String checkAuthC() {
		return "C is pass (No Secured)";
	}
}
