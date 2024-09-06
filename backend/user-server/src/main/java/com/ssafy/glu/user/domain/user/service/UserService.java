package com.ssafy.glu.user.domain.user.service;

import com.ssafy.glu.user.domain.user.dto.request.RegisterRequest;

public interface UserService {

	public Long register(RegisterRequest registerRequest);

}
