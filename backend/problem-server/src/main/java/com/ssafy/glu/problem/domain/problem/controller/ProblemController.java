package com.ssafy.glu.problem.domain.problem.controller;

import static com.ssafy.glu.problem.global.util.HeaderUtil.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/problems")
@Slf4j
public class ProblemController {
	private final ProblemService problemService;

	@GetMapping("/{problemId}")
	public ResponseEntity<ProblemBaseResponse> getProblem(@PathVariable("problemId") String problemId) {
		return ResponseEntity.status(HttpStatus.OK).body(problemService.getProblem(problemId));
	}

	@GetMapping("/solve")
	public ResponseEntity<Page<ProblemBaseResponse>> getProblemListInLog(
		@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId,
		@ModelAttribute ProblemSearchCondition condition, Pageable pageable) {
		log.info("condition : {}", condition);
		return ResponseEntity.status(HttpStatus.OK).body(problemService.getProblemList(userId, condition, pageable));
	}

	@PostMapping("/{problemId}/memo")
	public ResponseEntity<ProblemMemoResponse> createUserProblemMemo(
		@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId,
		@PathVariable("problemId") String problemId, @RequestBody ProblemMemoCreateRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(problemService.createProblemMemo(userId, problemId, request));
	}

	@PutMapping("/{problemId}/memo")
	public ResponseEntity<ProblemMemoResponse> updateUserProblemMemo(
		@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId,
		@PathVariable("problemId") String problemId, @RequestBody ProblemMemoUpdateRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(problemService.updateProblemMemo(userId, problemId, request));
	}

	@DeleteMapping("/{problemId}/memo")
	public ResponseEntity<Void> deleteUserProblemMemo(@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId,
		@PathVariable("problemId") String problemId, @RequestParam("memoIndex") Long memoIndex) {
		problemService.deleteProblemMemo(userId, problemId, memoIndex);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("/{problemId}/memo")
	public ResponseEntity<Page<ProblemMemoResponse>> getProblemMemoListInProblem(
		@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId,
		@PathVariable("problemId") String problemId, @PageableDefault(size = 4) Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(problemService.getProblemMemoList(userId, problemId, pageable));
	}

	@PostMapping("/{problemId}/favorite")
	public ResponseEntity<Void> createUserProblemFavorite(@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId,
		@PathVariable("problemId") String problemId) {
		problemService.createUserProblemFavorite(userId, problemId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/{problemId}/favorite")
	public ResponseEntity<Void> deleteUserProblemFavorite(@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId,
		@PathVariable("problemId") String problemId) {
		problemService.deleteUserProblemFavorite(userId, problemId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping("/{problemId}/grading")
	public ResponseEntity<ProblemGradingResponse> gradeProblem(
		@Parameter(hidden = true) @RequestHeader(USER_ID) Long userId,
		@PathVariable("problemId") String problemId,
		@RequestBody ProblemSolveRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(problemService.gradeProblem(userId, problemId, request));
	}
}