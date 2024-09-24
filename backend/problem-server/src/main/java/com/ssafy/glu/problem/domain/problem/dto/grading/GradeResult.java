package com.ssafy.glu.problem.domain.problem.dto.grading;

import com.ssafy.glu.problem.global.util.ScoreUtil;

import lombok.Builder;

@Builder
public record GradeResult (
	boolean isCorrect,
	int userLevel,
	int userScore,
	int acquiredScore
){
	public int updatedUserScore() {
		return userScore + acquiredScore;
	}

	public int totalUserScore() {
		return ScoreUtil.calculateTotalScore(userLevel,updatedUserScore());
	}
}
