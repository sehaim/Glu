package com.ssafy.glu.problem.domain.problem.dto.grading;

import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeCode;
import com.ssafy.glu.problem.global.util.ScoreUtil;

import lombok.Builder;

@Builder
public record GradeResult(
	boolean isCorrect,
	ProblemTypeCode problemTypeCode,
	int problemLevel,
	int userLevel,
	int userScore,
	int acquiredScore
) {
	public int updatedUserScore() {
		return userScore + acquiredScore;
	}

	public int totalUserScore() {
		return ScoreUtil.calculateTotalScore(userLevel, updatedUserScore());
	}
}
