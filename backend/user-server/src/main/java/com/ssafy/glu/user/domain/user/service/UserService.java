package com.ssafy.glu.user.domain.user.service;

import com.ssafy.glu.user.domain.user.dto.request.UserRegisterRequest;
import com.ssafy.glu.user.domain.user.dto.request.UserUpdateRequest;
import com.ssafy.glu.user.domain.user.dto.response.UserResponse;

public interface UserService {

	Long register(UserRegisterRequest userRegisterRequest);

	UserResponse getUser(Long userId);

	void updateUser(Long userId, UserUpdateRequest userUpdateRequest);

	void deleteUser(Long userId);

}
