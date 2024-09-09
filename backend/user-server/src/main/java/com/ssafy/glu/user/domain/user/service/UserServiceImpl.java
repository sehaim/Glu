package com.ssafy.glu.user.domain.user.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.glu.user.domain.user.domain.ProblemType;
import com.ssafy.glu.user.domain.user.domain.UserProblemType;
import com.ssafy.glu.user.domain.user.domain.Users;
import com.ssafy.glu.user.domain.user.dto.request.UserRegisterRequest;
import com.ssafy.glu.user.domain.user.dto.request.UserUpdateRequest;
import com.ssafy.glu.user.domain.user.dto.response.UserProblemTypeResponse;
import com.ssafy.glu.user.domain.user.dto.response.UserResponse;
import com.ssafy.glu.user.domain.user.exception.UserNotFoundException;
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
	public Long register(UserRegisterRequest userRegisterRequest) {

		String encodedPassword = passwordEncoder.encode(userRegisterRequest.password());

		Users user = Users.builder()
			.loginId(userRegisterRequest.loginId())
			.nickname(userRegisterRequest.nickname())
			.password(encodedPassword)
			.birth(userRegisterRequest.birth())
			.build();

		//유저 저장
		Users saveUser = userRepository.save(user);

		// 유저 문제타입 저장
		for (ProblemType problemType : ProblemType.values()) {

			UserProblemType userProblemType = UserProblemType.builder()
				.problemTypeCode(problemType)
				.user(saveUser)
				.build();

			userProblemTypeRepository.save(userProblemType);
		}

		return saveUser.getId();
	}

	/**
	 * 유저 정보 가져오기
	 */
	@Override
	public UserResponse getUser(Long userId) {

		Users findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
		List<UserProblemType> userProblemTypes = userProblemTypeRepository.findAllByUserId(userId);

		return UserResponse.builder()
			.id(userId)
			.dayCount(findUser.getDayCount())
			.score(findUser.getStage())
			.level(findUser.getExp())
			.imageUrl("tempImageURL")
			.nickname(findUser.getNickname())
			.problemTypeList(getProblemTypeLists(userProblemTypes))
			.build();
	}

	/**
	 * 변환 시키기 userProblemlist => ProblemTypeList
	 */
	private static List<UserProblemTypeResponse> getProblemTypeLists(List<UserProblemType> userProblemTypes) {
		return userProblemTypes.stream().map(UserProblemTypeResponse::of).toList();
	}

	/**
	 * 유저 업데이트
	 * 비밀번호 일치 체크후 업데이트
	 */
	@Transactional
	@Override
	public void updateUser(Long userId, UserUpdateRequest request) {

		Users findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

		//비밀번호 일치할때
		if (passwordEncoder.matches(request.password(), findUser.getPassword())) {
			String encodedPassword = passwordEncoder.encode(request.newPassword());
			//유저 정보 업데이트
			findUser.updateUser(encodedPassword, request.nickname());
		}
	}

	/**
	 * 유저 삭제
	 * softdelete를 위해 user의 isdelete만 변경
	 */
	@Transactional
	@Override
	public void deleteUser(Long userId) {
		Users findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

		//유저 삭제
		findUser.deleteUser();
	}

}
