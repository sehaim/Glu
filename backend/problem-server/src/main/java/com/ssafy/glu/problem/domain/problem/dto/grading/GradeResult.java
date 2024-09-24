package com.ssafy.glu.problem.domain.problem.dto.grading;

import lombok.Builder;

@Builder
public record GradeResult (
	boolean isCorrect,
	int userScore,
	int acquiredScore
){
	public int updatedUserScore() {
		return userScore + acquiredScore;
	}
}
