package com.ssafy.glu.user.domain.user.dto.request;

public record UserUpdateRequest(
	String nickname,
	String password,
	String newPassword
) {}
