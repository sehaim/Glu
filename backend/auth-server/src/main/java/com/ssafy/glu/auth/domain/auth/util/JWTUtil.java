package com.ssafy.glu.auth.domain.auth.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTUtil {

	private final SecretKey secretKey;

	public JWTUtil(@Value("${jwt.secret}") String secret) {
		this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
			Jwts.SIG.HS256.key().build().getAlgorithm());
	}

	public String getId(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("id", String.class);
	}

	public String getCategory(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("category", String.class);
	}

	public Boolean isExpired(String token) {
		Claims claims = Jwts.parser()
			.verifyWith(secretKey)  // 비밀 키 설정
			.build()
			.parseSignedClaims(token)     // JWT를 파싱
			.getPayload();                // 클레임 추출

		Date expiration = claims.getExpiration();  // 만료 시간 추출
		return expiration.before(new Date());      // 만료 여부 확인
	}

	public String createToken(String category, Long userId, String nickname, boolean isFirst, Long expiredMs) {

		return Jwts.builder()
			.claim("category", category)
			.claim("id", userId.toString())
			.claim("nickname", nickname)
			.claim("isFirst", isFirst)
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + expiredMs))
			.signWith(secretKey)
			.compact();
	}
}
