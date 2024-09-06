package com.ssafy.glu.user.domain.user.service;

import com.ssafy.glu.user.domain.user.dto.request.UserRegisterRequest;
import com.ssafy.glu.user.domain.user.dto.response.UserResponse;

public interface UserService {

	public Long register(UserRegisterRequest userRegisterRequest);

	public UserResponse getUser(Long userId);

}
