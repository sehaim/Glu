package com.ssafy.glu.user.domain.user.dto.request;

import java.time.LocalDate;

public record RegisterRequest (

	String loginId,
	String password,
	String nickname,
	LocalDate birth
){}
