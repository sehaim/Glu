package com.ssafy.glu.user.domain.user.dto.request;

import java.time.LocalDate;

public record UserUpdateRequest(
	String nickname,
	String password,
	String newPassword,
	LocalDate birth
) {}
