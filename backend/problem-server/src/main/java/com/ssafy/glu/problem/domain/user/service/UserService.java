package com.ssafy.glu.problem.domain.user.service;

import org.springframework.stereotype.Service;

import com.ssafy.glu.problem.global.feign.UserClient;
import com.ssafy.glu.problem.global.feign.dto.UserResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	private final UserClient userClient;

	public UserResponse getUser(Long userId) {
		return userClient.getUser(userId).getBody();
	}
}