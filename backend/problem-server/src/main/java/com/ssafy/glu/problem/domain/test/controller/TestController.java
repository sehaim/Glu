package com.ssafy.glu.problem.domain.test.controller;

import static com.ssafy.glu.problem.global.util.HeaderUtil.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.glu.problem.domain.test.dto.request.TestSolveRequest;
import com.ssafy.glu.problem.domain.test.dto.response.TestGradingResponse;
import com.ssafy.glu.problem.domain.test.service.TestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
@Slf4j
public class TestController {
	private final TestService testService;

	@PostMapping("/grading")
	public ResponseEntity<TestGradingResponse> getTestGradingResult(@RequestHeader(USER_ID) Long userId,
		@RequestBody TestSolveRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(testService.gradeTest(userId, request));
	}

}
