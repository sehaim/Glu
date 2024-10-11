package com.ssafy.glu.auth.domain.auth.controller;

import static com.ssafy.glu.auth.global.util.HeaderUtil.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.glu.auth.domain.auth.dto.request.LoginRequest;
import com.ssafy.glu.auth.domain.auth.exception.UserNotFoundException;
import com.ssafy.glu.auth.domain.auth.service.AuthService;
import com.ssafy.glu.auth.domain.auth.util.JWTUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "인증", description = "인증 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;
	private final JWTUtil jwtUtil;

	@Operation(summary = "로그인", description = "사용자 로그인을 처리합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "로그인 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/login")
	public ResponseEntity<Void> login (@RequestBody LoginRequest loginRequest, HttpServletResponse httpResponse) {
		log.info("Login request: {}", loginRequest);
		authService.login(loginRequest, httpResponse);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@Operation(summary = "로그아웃", description = "사용자 로그아웃을 처리합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "로그아웃 성공"),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/logout")
	public ResponseEntity<Void> logout (@RequestHeader(USER_ID) Long userId, HttpServletResponse httpResponse) {
		log.info("Logout request: {}", userId);
		authService.logout(userId, httpResponse);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Operation(summary = "토큰 재발급", description = "리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "토큰 재발급 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "401", description = "유효하지 않은 리프레시 토큰", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/reissue")
	public ResponseEntity<Void> reissue (HttpServletRequest request, HttpServletResponse httpResponse) {

		// Retrieve cookies from the request
		Cookie[] cookies = request.getCookies();
		String refreshToken = null;

		if (cookies != null) {
			for (Cookie cookie : cookies) {

				log.info("cookie name {} cookie value {} ", cookie.getName(), cookie.getValue());

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
