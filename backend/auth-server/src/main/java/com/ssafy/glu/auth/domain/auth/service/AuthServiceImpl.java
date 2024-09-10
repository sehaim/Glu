package com.ssafy.glu.auth.domain.auth.service;

import static com.ssafy.glu.auth.global.util.HeaderUtil.*;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.glu.auth.domain.auth.domain.Users;
import com.ssafy.glu.auth.domain.auth.dto.request.LoginRequest;
import com.ssafy.glu.auth.domain.auth.exception.LoginInValidException;
import com.ssafy.glu.auth.domain.auth.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

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

	}
}
