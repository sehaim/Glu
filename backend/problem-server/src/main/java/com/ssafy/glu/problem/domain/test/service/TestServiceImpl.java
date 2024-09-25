package com.ssafy.glu.problem.domain.test.service;

import org.springframework.stereotype.Service;

import com.ssafy.glu.problem.domain.test.dto.request.TestSolveRequest;
import com.ssafy.glu.problem.domain.test.dto.response.TestGradingResponse;
import com.ssafy.glu.problem.domain.test.repository.TestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {
	private final TestRepository testRepository;

	@Override
	public TestGradingResponse gradeTest(Long userId, TestSolveRequest request) {
		return null;
	}
}
