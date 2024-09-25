package com.ssafy.glu.problem.domain.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.glu.problem.domain.problem.dto.grading.GradeResult;
import com.ssafy.glu.problem.global.feign.UserClient;
import com.ssafy.glu.problem.global.feign.dto.ExpUpdateRequest;
import com.ssafy.glu.problem.global.feign.dto.ExpUpdateResponse;
import com.ssafy.glu.problem.global.feign.dto.UserResponse;
import com.ssafy.glu.problem.global.feign.exception.UserFeignException;

import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
	private final UserClient userClient;

	public UserResponse getUser(Long userId) {
		try {
			return userClient.getUser(userId).getBody();
		} catch (FeignException e) {
			log.info(e.toString());
			throw new UserFeignException(e);
		}
	}

	public ExpUpdateResponse updateUser(Long userId, ExpUpdateRequest updateRequest) {
		try {
			return userClient.updateExp(userId, updateRequest).getBody();
		} catch (FeignException e) {
			log.info(e.toString());
			throw new UserFeignException(e);
		}
	}

	public ExpUpdateResponse updateUserExp(UserResponse user, Long userId, GradeResult gradeResult) {
		return updateUserExp(user, userId, List.of(gradeResult));
	}

	public ExpUpdateResponse updateUserExp(UserResponse user, Long userId, List<GradeResult> gradeResult) {
		ExpUpdateRequest expUpdateRequest = ExpUpdateRequest.ofGradeResultList(user.problemTypeList(), gradeResult);
		log.info("[캐릭터 성장 요청] : {}", expUpdateRequest);
		return updateUser(userId, expUpdateRequest);
	}
}