package com.ssafy.glu.auth.domain.auth.controller;

import static com.ssafy.glu.auth.global.util.HeaderUtil.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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

	@PostMapping("/logout")
	public ResponseEntity<?> logout (@RequestHeader(USER_ID) Long userId, HttpServletResponse httpResponse) {
		authService.logout(userId, httpResponse);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
