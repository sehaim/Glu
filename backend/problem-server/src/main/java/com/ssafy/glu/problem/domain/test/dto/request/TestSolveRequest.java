package com.ssafy.glu.problem.domain.test.dto.request;

import java.util.List;

import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSolveRequest;

public record TestSolveRequest(
	Integer totalSolvedTime,    // 전체 풀이 시간
	List<ProblemSolveRequest> problemSolveRequestList
) {
}
