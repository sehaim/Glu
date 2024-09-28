package com.ssafy.glu.problem.domain.problem.dto.grading;

import java.util.Map;

import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeCode;

import lombok.Getter;

@Getter
public class UserScore {
	private Map<ProblemTypeCode, ProblemTypeScore> scoreByProblemType;

	// 특정 문제 유형의 점수 가져오기
	public int getScore(ProblemTypeCode problemType) {
		return scoreByProblemType.get(problemType).getScore();
	}

	// 특정 문제 유형의 레벨 가져오기
	public int getLevel(ProblemTypeCode problemType) {
		return scoreByProblemType.get(problemType).getLevel();
	}

	// 특정 문제 유형의 총 점수 가져오기
	public int getTotalScore(ProblemTypeCode problemType) {
		return scoreByProblemType.get(problemType).getTotalScore();
	}

	// 특정 문제 유형의 점수와 레벨 업데이트
	public void updateScore(ProblemTypeCode problemType, int acquiredScore) {
		scoreByProblemType.get(problemType).updateScore(acquiredScore);
	}

	// 전체 점수 계산 (선택 사항: 모든 유형의 총합)
	public int getTotalScore() {
		return scoreByProblemType.values().stream().mapToInt(ProblemTypeScore::getTotalScore).sum();
	}
}