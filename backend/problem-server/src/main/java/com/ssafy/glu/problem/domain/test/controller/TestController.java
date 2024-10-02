package com.ssafy.glu.problem.domain.test.controller;

import static com.ssafy.glu.problem.global.util.HeaderUtil.*;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.glu.problem.domain.test.dto.request.TestSolveRequest;
import com.ssafy.glu.problem.domain.test.dto.response.TestGradingBaseResponse;
import com.ssafy.glu.problem.domain.test.dto.response.TestGradingDetailResponse;
import com.ssafy.glu.problem.domain.test.dto.response.TestGradingResponse;
import com.ssafy.glu.problem.domain.test.service.TestService;
import com.ssafy.glu.problem.global.dto.PageResponse;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tests")
@Slf4j
public class TestController {
	private final TestService testService;
	// TODO: API Swagger 문서화

	@PostMapping("/grading")
	public ResponseEntity<TestGradingResponse> getTestGradingResult(
		@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId, @RequestBody TestSolveRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(testService.gradeTest(userId, request));
	}

	@GetMapping
	public ResponseEntity<PageResponse<TestGradingDetailResponse>> getTestList(
		@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId, Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(new PageResponse<>(testService.getTestList(userId, pageable)));
	}

	@GetMapping("/previous")
	public ResponseEntity<TestGradingBaseResponse> getPreviousTest(
		@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(testService.getPreviousTest(userId));
	}

	@GetMapping("/{testId}")
	public ResponseEntity<TestGradingDetailResponse> getTest(
		@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId, @PathVariable("testId") String testId) {
		return ResponseEntity.status(HttpStatus.OK).body(testService.getTest(userId, testId));
	}
}
