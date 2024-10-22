package com.ssafy.glu.problem.domain.problem.service;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.dto.grading.GradeResult;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSolveRequest;
import com.ssafy.glu.problem.global.feign.dto.UserResponse;

public interface ProblemGradingService {
	GradeResult gradeProblem(UserResponse user, Problem problem, ProblemSolveRequest request);
}