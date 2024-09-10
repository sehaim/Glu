package com.ssafy.glu.auth.domain.auth.service;

public interface JwtTokenService {

	public void saveRefreshToken(Long userId, String token);

	public String getRefreshToken(Long userId);

	public void deleteRefreshToken(Long userId);

}
