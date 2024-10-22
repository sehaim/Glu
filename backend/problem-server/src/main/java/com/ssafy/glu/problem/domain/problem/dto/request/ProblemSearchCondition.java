package com.ssafy.glu.problem.domain.problem.dto.request;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.ProblemLevelCode;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeCode;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeDetailCode;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "문제 검색 조건 DTO")
public record ProblemSearchCondition(
	@Schema(description = "문제 상태")
	Problem.Status status,

	@Schema(description = "문제 유형 코드")
	ProblemTypeCode problemTypeCode,

	@Schema(description = "문제 유형 세부 코드")
	ProblemTypeDetailCode problemTypeDetailCode,

	@Schema(description = "문제 난이도 코드")
	ProblemLevelCode problemLevelCode,

	@Schema(description = "메모 유무")
	Boolean hasMemo,

	@Schema(description = "즐겨찾기 유무")
	Boolean isFavorite
) {
}