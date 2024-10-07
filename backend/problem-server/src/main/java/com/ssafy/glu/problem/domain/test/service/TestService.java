package com.ssafy.glu.problem.domain.test.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.glu.problem.domain.test.dto.request.TestSolveRequest;
import com.ssafy.glu.problem.domain.test.dto.response.TestGradingBaseResponse;
import com.ssafy.glu.problem.domain.test.dto.response.TestGradingDetailResponse;
import com.ssafy.glu.problem.domain.test.dto.response.TestGradingResponse;

public interface TestService {
	TestGradingResponse gradeTest(Long userId, TestSolveRequest request);

	Page<TestGradingBaseResponse> getTestList(Long userId, Pageable pageable);

	TestGradingDetailResponse getTest(Long userId, String testId);

	TestGradingBaseResponse getPreviousTest(Long userId);
}
