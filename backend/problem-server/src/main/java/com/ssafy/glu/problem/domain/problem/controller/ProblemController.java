package com.ssafy.glu.problem.domain.problem.controller;

import static com.ssafy.glu.problem.global.util.HeaderUtil.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoCreateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoUpdateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSearchCondition;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSolveRequest;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemBaseResponse;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemGradingResponse;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemMemoResponse;
import com.ssafy.glu.problem.domain.problem.service.ProblemService;
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

@Tag(name = "문제", description = "문제 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/problems")
@Slf4j
public class ProblemController {
	private final ProblemService problemService;

	@Operation(summary = "문제 조회", description = "문제 ID로 특정 문제의 세부 정보를 조회합니다. 문제 제목, 내용, 메타데이터 등을 포함합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "문제 조회 성공", content = @Content(schema = @Schema(implementation = ProblemBaseResponse.class))),
		@ApiResponse(responseCode = "404", description = "문제를 찾을 수 없습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
	@GetMapping("/{problemId}")
	public ResponseEntity<ProblemBaseResponse> getProblem(@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId,
		@PathVariable("problemId") String problemId) {
		return ResponseEntity.status(HttpStatus.OK).body(problemService.getProblem(userId, problemId));
	}

	@Operation(summary = "풀이한 문제 목록 조회", description = "사용자가 풀이한 문제의 목록을 필터링 조건에 따라 페이징하여 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "목록 조회 성공", content = @Content(schema = @Schema(implementation = PageResponse.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
	@GetMapping("/solve")
	public ResponseEntity<PageResponse<ProblemBaseResponse>> getProblemListInLog(
		@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId, @ModelAttribute ProblemSearchCondition condition,
		Pageable pageable) {
		log.info("condition : {}", condition);
		Page<ProblemBaseResponse> pageResult = problemService.getProblemList(userId, condition, pageable);
		return ResponseEntity.status(HttpStatus.OK).body(new PageResponse<>(pageResult));
	}

	@Operation(summary = "문제 메모 생성", description = "특정 문제에 대한 메모를 추가합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "메모가 성공적으로 생성되었습니다", content = @Content(schema = @Schema(implementation = ProblemMemoResponse.class))),
		@ApiResponse(responseCode = "404", description = "문제나 메모를 찾을 수 없습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
	@PostMapping("/{problemId}/memo")
	public ResponseEntity<ProblemMemoResponse> createUserProblemMemo(
		@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId, @PathVariable("problemId") String problemId,
		@RequestBody ProblemMemoCreateRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(problemService.createProblemMemo(userId, problemId, request));
	}

	@Operation(summary = "문제 메모 수정", description = "특정 문제에 대한 기존 메모를 수정합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "메모가 성공적으로 수정되었습니다", content = @Content(schema = @Schema(implementation = ProblemMemoResponse.class))),
		@ApiResponse(responseCode = "404", description = "문제나 메모를 찾을 수 없습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
	@PutMapping("/{problemId}/memo")
	public ResponseEntity<ProblemMemoResponse> updateUserProblemMemo(
		@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId, @PathVariable("problemId") String problemId,
		@RequestBody ProblemMemoUpdateRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(problemService.updateProblemMemo(userId, problemId, request));
	}

	@Operation(summary = "문제 메모 삭제", description = "특정 문제에 대한 메모를 삭제합니다.")
	@ApiResponses(value = {@ApiResponse(responseCode = "204", description = "메모가 성공적으로 삭제되었습니다"),
		@ApiResponse(responseCode = "404", description = "메모를 찾을 수 없습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
	@DeleteMapping("/{problemId}/memo")
	public ResponseEntity<Void> deleteUserProblemMemo(@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId,
		@PathVariable("problemId") String problemId, @RequestParam("memoIndex") Long memoIndex) {
		problemService.deleteProblemMemo(userId, problemId, memoIndex);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Operation(summary = "문제 즐겨찾기 여부 조회", description = "특정 문제에 대해 사용자가 즐겨찾기한 상태인지 확인합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "즐겨찾기 여부 조회 성공", content = @Content(schema = @Schema(implementation = Boolean.class))),
		@ApiResponse(responseCode = "404", description = "문제를 찾을 수 없습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
	@GetMapping("/{problemId}/favorite")
	public ResponseEntity<Boolean> getUserProblemFavorite(@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId,
		@PathVariable("problemId") String problemId) {
		return ResponseEntity.status(HttpStatus.OK).body(problemService.getIsFavorite(userId, problemId));
	}

	@Operation(summary = "문제 즐겨찾기 등록", description = "특정 문제를 즐겨찾기에 추가합니다.")
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "즐겨찾기 등록 성공"),
		@ApiResponse(responseCode = "409", description = "이미 즐겨찾기에 등록된 문제입니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
	@PostMapping("/{problemId}/favorite")
	public ResponseEntity<Void> createUserProblemFavorite(@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId,
		@PathVariable("problemId") String problemId) {
		problemService.createUserProblemFavorite(userId, problemId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "문제 즐겨찾기 삭제", description = "특정 문제를 즐겨찾기에서 삭제합니다.")
	@ApiResponses(value = {@ApiResponse(responseCode = "204", description = "즐겨찾기 삭제 성공"),
		@ApiResponse(responseCode = "404", description = "찜한 문제가 아닙니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
	@DeleteMapping("/{problemId}/favorite")
	public ResponseEntity<Void> deleteUserProblemFavorite(@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId,
		@PathVariable("problemId") String problemId) {
		problemService.deleteUserProblemFavorite(userId, problemId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Operation(summary = "문제 채점", description = "특정 문제에 대해 사용자의 답안을 채점합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "채점 성공", content = @Content(schema = @Schema(implementation = ProblemGradingResponse.class))),
		@ApiResponse(responseCode = "404", description = "문제를 찾을 수 없습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
	@PostMapping("/{problemId}/grading")
	public ResponseEntity<ProblemGradingResponse> gradeProblem(
		@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId, @PathVariable("problemId") String problemId,
		@RequestBody ProblemSolveRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(problemService.gradeProblem(userId, problemId, request));
	}
}
