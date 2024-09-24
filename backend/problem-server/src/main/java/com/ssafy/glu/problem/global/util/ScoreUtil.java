package com.ssafy.glu.problem.global.util;

import com.ssafy.glu.problem.global.feign.dto.UserProblemTypeResponse;

public class ScoreUtil {
	public final static int REQUIRED_SCORE = 100;

	// 특정 문제 유형의 총 점수 가져오기
	public static int calculateTotalScore(int level, int score) {
		return level * REQUIRED_SCORE + score;
	}

	public static int calculateTotalScore(UserProblemTypeResponse userProblemType) {
		return calculateTotalScore(userProblemType.level(), userProblemType.score());
	}

	public static int calculateNewScore(int currentScore, int problemScore, boolean isCorrect) {
		return EloRatingUtil.calculateNewScore(currentScore, problemScore, isCorrect);
	}
	public static int calculateAcquiredScore(int currentScore, int problemScore, boolean isCorrect) {
		return EloRatingUtil.calculateAcquiredScore(currentScore, problemScore, isCorrect);
	}
}