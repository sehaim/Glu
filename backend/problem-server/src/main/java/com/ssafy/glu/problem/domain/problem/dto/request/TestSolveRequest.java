package com.ssafy.glu.problem.domain.problem.dto.request;

import java.util.List;

public record TestSolveRequest(
	Integer totalSolvedTime,	// 전체 풀이 시간
	List<ProblemSolveRequest> problemSolveRequestList
) {
}
