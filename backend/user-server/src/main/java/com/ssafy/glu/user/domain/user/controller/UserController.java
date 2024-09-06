package com.ssafy.glu.user.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.glu.user.domain.user.dto.request.RegisterRequest;
import com.ssafy.glu.user.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> login(RegisterRequest registerRequest) {
		userService.register(registerRequest);
		return ResponseEntity.ok("가입 완료");
	}

}
