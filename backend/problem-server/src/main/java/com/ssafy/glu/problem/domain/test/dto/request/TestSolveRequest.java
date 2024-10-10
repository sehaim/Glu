package com.ssafy.glu.problem.domain.test.dto.request;

import java.util.List;

import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSolveRequest;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "테스트 풀이 요청 DTO")
public record TestSolveRequest(
	@Schema(description = "전체 풀이 시간", example = "300")
	Integer totalSolvedTime,

	@Schema(description = "문제 풀이 요청 목록")
	List<ProblemSolveRequest> problemSolveRequestList
) {
}
