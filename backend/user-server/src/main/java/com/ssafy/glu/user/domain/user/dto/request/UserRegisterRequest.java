package com.ssafy.glu.user.domain.user.dto.request;

import java.time.LocalDate;

public record UserRegisterRequest(
	String id,
	String password,
	String nickname,
	LocalDate birth
){}
