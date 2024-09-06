package com.ssafy.glu.user.domain.user.service;

import java.time.LocalDate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.glu.user.domain.user.domain.UserProblemType;
import com.ssafy.glu.user.domain.user.domain.Users;
import com.ssafy.glu.user.domain.user.dto.request.RegisterRequest;
import com.ssafy.glu.user.domain.user.repository.UserProblemTypeRepository;
import com.ssafy.glu.user.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserProblemTypeRepository userProblemTypeRepository;

	private final BCryptPasswordEncoder passwordEncoder;

	/**
	 * 유저 저장
	 * 저장할때 문제타입도 만들어서 같이 저장시키기
	 * PT01	어휘 및 문법
	 * PT02	독해 
	 * PT03	추론
	 */
	@Transactional
	@Override
	public Long register(RegisterRequest registerRequest) {

		String encodedPassword = passwordEncoder.encode(registerRequest.password());

		Users user = new Users(registerRequest.loginId(),
			registerRequest.nickname(),
			encodedPassword,
			registerRequest.birth());

		//유저 저장
		Users saveUser = userRepository.save(user);

		//유저 문제타입 저장유형코드
		String[] codes = new String[] {"PT01", "PT02", "PT03"};
		for (String code : codes) {
			userProblemTypeRepository.save(new UserProblemType(saveUser, code));
		}

		return saveUser.getId();
	}
}
