package com.ssafy.glu.auth.domain.auth.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ssafy.glu.auth.domain.auth.exception.JwtExpiredException;
import com.ssafy.glu.auth.domain.auth.exception.JwtMalformedException;
import com.ssafy.glu.auth.domain.auth.exception.JwtSecurityException;
import com.ssafy.glu.auth.domain.auth.exception.JwtUnsupportedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
public class JWTUtil {
	private final SecretKey key; // secret Key
	@Value("${jwt.claims-name.user-id}")
	public String CLAIM_NAME_USER_ID;
	public JWTUtil(@Value("${jwt.secret}") String secretKey) {
		this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}

	public Claims verifyToken(String token) {
		try {
			return Jwts.parser()
				.verifyWith(key).build()
				.parseSignedClaims(token)
				.getPayload();
		} catch (SecurityException e) {
			throw new JwtSecurityException(e);
		} catch (MalformedJwtException e){
			throw new JwtMalformedException(e);
		} catch (ExpiredJwtException e) {
			throw new JwtExpiredException(e);
		} catch (UnsupportedJwtException e) {
			throw new JwtUnsupportedException(e);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public Long getUserId(Claims claims) {
		return claims.get(CLAIM_NAME_USER_ID,Long.class);
	}

	public String createToken(String category, Long userId, String nickname, boolean isFirst, Long expiredMs) {

		return Jwts.builder()
			.claim("category", category)
			.claim(CLAIM_NAME_USER_ID, userId)
			.claim("nickname", nickname)
			.claim("isFirst", isFirst)
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + expiredMs))
			.signWith(key)
			.compact();
	}

}