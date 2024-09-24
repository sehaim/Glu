package com.ssafy.glu.user.domain.user.dto.grading;

public record GradeResult (
	boolean isCorrect,
	int userLevel,
	int userScore,
	int acquiredScore
) {}
