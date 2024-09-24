package com.ssafy.glu.user.domain.user.dto.event;

import com.ssafy.glu.user.domain.user.dto.grading.GradeResult;

public record ProblemSolvedEvent(
	Long userId,
	ProblemCodeResponse problem,
	GradeResult result,
	String userAnswer,
	int solvedTime
) {}
