package com.ssafy.glu.auth.domain.auth.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtTokenServiceTest {

	@Autowired
	private JwtTokenService jwtTokenService;

	@Test
	void saveRefreshToken() {
		// Arrange
		Long userId = 1L;
		String refreshToken = "dummyRefreshToken";

		jwtTokenService.saveRefreshToken(userId, refreshToken);

		assertEquals(refreshToken, jwtTokenService.getRefreshToken(userId));

	}

	@Test
	void getRefreshToken() {
		// Arrange
		Long userId = 1L;
		String refreshToken = "dummyRefreshToken";

		jwtTokenService.saveRefreshToken(userId, refreshToken);

		assertEquals(refreshToken, jwtTokenService.getRefreshToken(userId));
	}

	@Test
	void deleteRefreshToken() {
		// Arrange
		Long userId = 1L;;
		String refreshToken = "dummyRefreshToken";

		jwtTokenService.saveRefreshToken(userId, refreshToken);

		jwtTokenService.deleteRefreshToken(userId);

		assertNull(jwtTokenService.getRefreshToken(userId));
	}



}