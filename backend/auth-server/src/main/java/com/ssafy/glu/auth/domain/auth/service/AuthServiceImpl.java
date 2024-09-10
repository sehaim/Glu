package com.ssafy.glu.auth.domain.auth.service;

import static com.ssafy.glu.auth.global.util.HeaderUtil.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.glu.auth.domain.auth.domain.Users;
import com.ssafy.glu.auth.domain.auth.dto.request.LoginRequest;
import com.ssafy.glu.auth.domain.auth.exception.LoginInValidException;
import com.ssafy.glu.auth.domain.auth.repository.UserRepository;
import com.ssafy.glu.auth.domain.auth.util.JWTUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtTokenService jwtTokenService;
	private final JWTUtil jwtUtil;

	@Value("${jwt.access}")
	private Long accessTime;
	@Value("${jwt.refresh}")
	private Long refreshTime;

	/**
	 * 로그인시 디비에 없다면 loginInvalidException
	 * 헤더에 아이디 쿠키 레디스에 토큰 저장
	 */
	@Override
	public void login(LoginRequest loginRequest, HttpServletResponse httpResponse) {

		Users findUser = userRepository.findByLoginId(loginRequest.id()).orElseThrow(LoginInValidException::new);

		if (!passwordEncoder.matches(loginRequest.password(), findUser.getPassword())) {
			throw new LoginInValidException();
		}

		//헤더에 id저장
		httpResponse.addHeader(USER_ID, findUser.getId().toString());

		//토큰 생성
		String accessToken = jwtUtil.createToken("access", findUser.getId(), accessTime);
		String refreshToken = jwtUtil.createToken("refresh", findUser.getId(), refreshTime);

		//쿠키에 저장
		httpResponse.addCookie(createCookie("access", accessToken, accessTime/1000));
		httpResponse.addCookie(createCookie("refresh", refreshToken, refreshTime/1000));

		//레디스에 저장
		jwtTokenService.saveRefreshToken(findUser.getId(), refreshToken);

	}

	private Cookie createCookie(String key, String value, Long time) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(Math.toIntExact(time));
		cookie.setSecure(true);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		return cookie;
	}

	private Cookie removeCookie(String key) {
		Cookie cookie = new Cookie(key, ""); // 값은 빈 문자열로 설정
		cookie.setMaxAge(0); // 쿠키를 즉시 만료시킴
		cookie.setPath("/"); // 모든 경로에서 쿠키를 사용할 수 있도록 설정
		return cookie;
	}
}
