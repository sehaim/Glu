package com.ssafy.glu.user.domain.user.dto.response;

import com.ssafy.glu.user.domain.user.domain.ProblemType;

public record ProblemTypeList
	(Integer level, Integer score, ProblemType type) {}
