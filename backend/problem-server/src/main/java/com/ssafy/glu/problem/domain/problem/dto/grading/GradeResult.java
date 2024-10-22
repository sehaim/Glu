package com.ssafy.glu.problem.domain.problem.dto.grading;

import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeCode;
import com.ssafy.glu.problem.global.util.ScoreUtil;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "채점 결과 DTO")
public record GradeResult(
	@Schema(description = "정답 여부", example = "true")
	boolean isCorrect,

	@Schema(description = "문제 유형 코드")
	ProblemTypeCode problemTypeCode,

	@Schema(description = "문제 난이도", example = "1")
	int problemLevel,

	@Schema(description = "사용자 레벨", example = "2")
	int userLevel,

	@Schema(description = "사용자 점수", example = "1")
	int userScore,

	@Schema(description = "획득 점수", example = "100")
	int acquiredScore
) {
	public int updatedUserScore() {
		return userScore + acquiredScore;
	}

	public int totalUserScore() {
		return ScoreUtil.calculateTotalScore(userLevel, updatedUserScore());
	}
}
