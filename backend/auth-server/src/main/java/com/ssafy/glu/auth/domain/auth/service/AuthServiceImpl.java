package com.ssafy.glu.auth.domain.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.glu.auth.domain.auth.domain.Users;
import com.ssafy.glu.auth.domain.auth.dto.request.LoginRequest;
import com.ssafy.glu.auth.domain.auth.exception.LoginInValidException;
import com.ssafy.glu.auth.domain.auth.exception.RefreshTokenExpiredException;
import com.ssafy.glu.auth.domain.auth.exception.UserNotFoundException;
import com.ssafy.glu.auth.domain.auth.repository.UserRepository;
import com.ssafy.glu.auth.domain.auth.util.JWTUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtTokenService jwtTokenService;
	private final JWTUtil jwtUtil;

	@Value("${jwt.access.expiration}")
	private Long accessTime;
	@Value("${jwt.refresh.expiration}")
	private Long refreshTime;

	/**
	 * 로그인시 디비에 없다면 loginInvalidException
	 * 헤더에 아이디 쿠키 레디스에 토큰 저장
	 */
	@Override
	public void login(LoginRequest loginRequest, HttpServletResponse httpResponse) {

		String encodePass = passwordEncoder.encode(loginRequest.password());

		Optional<Users> findUser = userRepository.findByLoginIdAndPassword(loginRequest.id(), encodePass);

		if (findUser.isEmpty()) {
			throw new LoginInValidException();
		}

		boolean isFirst = findUser.get().getStage() == 0;

		tokenSave(httpResponse, findUser.get().getId(), findUser.get().getNickname(), isFirst);
	}


	@Override
	public void logout(Long userId, HttpServletResponse httpResponse) {

		//레디스에서 삭제
		jwtTokenService.deleteRefreshToken(userId);

		//쿠키에서 토큰 삭제
		httpResponse.addCookie(removeCookie("access"));
		httpResponse.addCookie(removeCookie("refresh"));
	}

	@Override
	public void reissue(String refreshToken, HttpServletResponse httpResponse) {

		Claims claims = jwtUtil.verifyToken(refreshToken);

		Long userId = jwtUtil.getUserId(claims);

		Users findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

		boolean isFirst = findUser.getStage() == 0;

		tokenSave(httpResponse, userId, findUser.getNickname(), isFirst);
	}

	private void tokenSave(HttpServletResponse httpResponse, Long userId, String nickname, boolean isFirst) {
		//토큰 생성
		String accessToken = jwtUtil.createToken("access", userId, nickname, isFirst, accessTime);
		String refreshToken = jwtUtil.createToken("refresh", userId, nickname, isFirst, refreshTime);

		//쿠키에 저장
		httpResponse.addCookie(createCookie("access", accessToken, accessTime/1000));
		httpResponse.addCookie(createCookie("refresh", refreshToken, refreshTime/1000));

		//response 헤더에 access토큰 저장
		httpResponse.setHeader("accessToken", accessToken);

		//레디스에 저장
		jwtTokenService.saveRefreshToken(userId, refreshToken);
	}

	private Cookie createCookie(String key, String value, Long time) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(Math.toIntExact(time));
		// cookie.setSecure(true);
		cookie.setPath("/");
		// cookie.setHttpOnly(true);
		return cookie;
	}

	private Cookie removeCookie(String key) {
		Cookie cookie = new Cookie(key, ""); // 값은 빈 문자열로 설정
		cookie.setMaxAge(0); // 쿠키를 즉시 만료시킴
		cookie.setPath("/"); // 모든 경로에서 쿠키를 사용할 수 있도록 설정
		return cookie;
	}
}
