package com.ssafy.glu.auth.domain.auth.service;

import com.ssafy.glu.auth.domain.auth.dto.request.LoginRequest;

import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

	void login(LoginRequest loginRequest, HttpServletResponse httpResponse);

}
