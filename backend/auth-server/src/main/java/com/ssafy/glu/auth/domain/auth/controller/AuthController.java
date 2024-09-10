package com.ssafy.glu.auth.domain.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.glu.auth.domain.auth.dto.request.LoginRequest;
import com.ssafy.glu.auth.domain.auth.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> login (LoginRequest loginRequest, HttpServletResponse httpResponse) {
		authService.login(loginRequest, httpResponse);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
