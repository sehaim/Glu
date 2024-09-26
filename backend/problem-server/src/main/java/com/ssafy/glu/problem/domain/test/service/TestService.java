package com.ssafy.glu.problem.domain.test.service;

import com.ssafy.glu.problem.domain.test.dto.request.TestSolveRequest;
import com.ssafy.glu.problem.domain.test.dto.response.TestGradingResponse;

public interface TestService {
	TestGradingResponse gradeTest(Long userId, TestSolveRequest request);
	// TestGradingBaseResponse getPreviousTest(Long userId);
	// List<TestGradingDetailResponse> getTestList(Long userId, Pageable pageable);
}
