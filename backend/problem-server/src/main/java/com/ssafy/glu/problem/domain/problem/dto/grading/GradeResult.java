package com.ssafy.glu.problem.domain.problem.dto.grading;

import lombok.Builder;

@Builder
public record GradeResult (
	boolean isCorrect,
	int userScore,
	int updatedUserScore,
	int acquiredScore
){

}
