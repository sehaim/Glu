package com.ssafy.glu.user.domain.user.dto.event;

import com.ssafy.glu.user.domain.user.domain.ProblemLevelCode;
import com.ssafy.glu.user.domain.user.domain.ProblemTypeCode;
import com.ssafy.glu.user.domain.user.domain.ProblemTypeDetailCode;
import com.ssafy.glu.user.domain.user.domain.QuestionTypeCode;

public record ProblemCodeResponse(
	String problemId,
	QuestionTypeCode questionTypeCode,
	ProblemLevelCode problemLevelCode,
	ProblemTypeDetailCode problemTypeDetailCode,
	ProblemTypeCode problemTypeCode
) {}
