package com.ssafy.glu.problem.domain.test.controller;

import static com.ssafy.glu.problem.global.util.HeaderUtil.*;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "테스트", description = "테스트 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tests")
@Slf4j
public class TestController {
	private final TestService testService;

	@Operation(summary = "테스트 채점", description = "사용자가 풀이한 문제들에 대해 채점을 수행하고, 유형별 및 문제별 채점 결과를 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "테스트 채점 성공", content = @Content(schema = @Schema(implementation = TestGradingResponse.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
	@PostMapping("/grading")
	public ResponseEntity<TestGradingResponse> getTestGradingResult(
		@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId, @RequestBody TestSolveRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(testService.gradeTest(userId, request));
	}

	@Operation(summary = "테스트 목록 조회", description = "사용자가 참여한 테스트 목록을 페이징 처리하여 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "테스트 목록 조회 성공", content = @Content(schema = @Schema(implementation = PageResponse.class))),
		@ApiResponse(responseCode = "404", description = "테스트 목록을 찾을 수 없습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
	@GetMapping
	public ResponseEntity<PageResponse<TestGradingDetailResponse>> getTestList(
		@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId, Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(new PageResponse<>(testService.getTestList(userId, pageable)));
	}

	@Operation(summary = "이전 테스트 결과 조회", description = "사용자가 가장 최근에 응시한 테스트 결과를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "이전 테스트 결과 조회 성공", content = @Content(schema = @Schema(implementation = TestGradingBaseResponse.class))),
		@ApiResponse(responseCode = "404", description = "이전 테스트 결과를 찾을 수 없습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
	@GetMapping("/previous")
	public ResponseEntity<TestGradingBaseResponse> getPreviousTest(
		@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(testService.getPreviousTest(userId));
	}

	@Operation(summary = "특정 테스트 결과 조회", description = "특정 테스트의 세부 결과를 조회합니다. 문제별 채점 결과와 유형별 결과를 포함합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "테스트 결과 조회 성공", content = @Content(schema = @Schema(implementation = TestGradingDetailResponse.class))),
		@ApiResponse(responseCode = "404", description = "테스트 결과를 찾을 수 없습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
	@GetMapping("/{testId}")
	public ResponseEntity<TestGradingDetailResponse> getTest(
		@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId, @PathVariable("testId") String testId) {
		return ResponseEntity.status(HttpStatus.OK).body(testService.getTest(userId, testId));
	}
}
