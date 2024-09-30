package com.ssafy.glu.auth.domain.auth.controller;

import static com.ssafy.glu.auth.global.util.HeaderUtil.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.glu.auth.domain.auth.dto.request.LoginRequest;
import com.ssafy.glu.auth.domain.auth.exception.UserNotFoundException;
import com.ssafy.glu.auth.domain.auth.service.AuthService;
import com.ssafy.glu.auth.domain.auth.util.JWTUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;
	private final JWTUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<Void> login (@RequestBody LoginRequest loginRequest, HttpServletResponse httpResponse) {
		log.info("Login request: {}", loginRequest);
		authService.login(loginRequest, httpResponse);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout (@RequestHeader(USER_ID) Long userId, HttpServletResponse httpResponse) {
		log.info("Logout request: {}", userId);
		authService.logout(userId, httpResponse);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping("/reissue")
	public ResponseEntity<Void> reissue (HttpServletRequest request, HttpServletResponse httpResponse) {

		// Retrieve cookies from the request
		Cookie[] cookies = request.getCookies();
		String refreshToken = null;

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("refresh".equals(cookie.getName())) {
					refreshToken = cookie.getValue();
					break;
				}
			}
		}

		log.info("Reissue request refreshToken: {}", refreshToken);

		authService.reissue(refreshToken, httpResponse);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
