package com.ssafy.glu.gateway.util;

import com.ssafy.glu.gateway.exception.*;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtProvider {
    private final SecretKey key; // secret Key
	@Value("${jwt.claims-name.user-id}")
	public String CLAIM_NAME_USER_ID;
    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
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

}