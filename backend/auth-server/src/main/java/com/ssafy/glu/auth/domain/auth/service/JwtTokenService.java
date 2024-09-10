package com.ssafy.glu.auth.domain.auth.service;

public interface JwtTokenService {

	void saveRefreshToken(Long userId, String token);

	String getRefreshToken(Long userId);

	void deleteRefreshToken(Long userId);

}
