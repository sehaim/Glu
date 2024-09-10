package com.ssafy.glu.auth.domain.auth.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

	private final RedisTemplate<String, String> redisTemplate;

	@Value("${jwt.refresh.expiration}")
	private long REFRESH_TOKEN_EXPIRATION; // 1 week in seconds

	public void saveRefreshToken(Long userId, String token) {
		String key = "refresh_token:" + userId;
		redisTemplate.opsForValue().set(key, token, REFRESH_TOKEN_EXPIRATION, TimeUnit.MILLISECONDS);
	}

	public String getRefreshToken(Long userId) {
		String key = "refresh_token:" + userId;
		return redisTemplate.opsForValue().get(key);
	}

	public void deleteRefreshToken(Long userId) {
		String key = "refresh_token:" + userId;
		redisTemplate.delete(key);
	}
}