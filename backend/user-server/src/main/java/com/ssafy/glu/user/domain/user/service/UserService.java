package com.ssafy.glu.user.domain.user.service;

import com.ssafy.glu.user.domain.user.dto.request.UserRegisterRequest;

public interface UserService {

	public Long register(UserRegisterRequest userRegisterRequest);

}
