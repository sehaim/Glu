package com.ssafy.glu.user.domain.user.dto.event;

import com.ssafy.glu.user.domain.user.domain.ProblemLevelCode;
import com.ssafy.glu.user.domain.user.domain.ProblemTypeCode;
import com.ssafy.glu.user.domain.user.domain.ProblemTypeDetailCode;

public record ProblemCodeResponse(
	String problemId,
	ProblemLevelCode problemLevelCode,
	ProblemTypeDetailCode problemTypeDetailCode,
	ProblemTypeCode problemTypeCode
) {}
