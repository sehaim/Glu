package com.ssafy.glu.user.domain.user.service;

import java.util.List;

import com.ssafy.glu.user.domain.user.dto.request.AttendanceRequest;
import com.ssafy.glu.user.domain.user.dto.request.ExpUpdateRequest;
import com.ssafy.glu.user.domain.user.dto.request.UserRegisterRequest;
import com.ssafy.glu.user.domain.user.dto.request.UserUpdateRequest;
import com.ssafy.glu.user.domain.user.dto.response.AttendanceResponse;
import com.ssafy.glu.user.domain.user.dto.response.ExpUpdateResponse;
import com.ssafy.glu.user.domain.user.dto.response.UserResponse;

public interface UserService {

	Long register(UserRegisterRequest userRegisterRequest);

	UserResponse getUser(Long userId);

	void updateUser(Long userId, UserUpdateRequest userUpdateRequest);

	void deleteUser(Long userId);

	Boolean checkUser(String loginId);

	List<AttendanceResponse> getAttendance(Long userId, AttendanceRequest request);

	ExpUpdateResponse updateExp(Long userId, ExpUpdateRequest expUpdateRequest);

	List<UserResponse> getAll();

}
