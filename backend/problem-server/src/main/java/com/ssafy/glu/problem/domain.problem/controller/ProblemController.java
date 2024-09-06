package com.ssafy.glu.problem.domain.problem.controller;

import static com.ssafy.glu.problem.global.util.HeaderUtil.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoUpdateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.UserProblemLogSearchCondition;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemMemoResponse;
import com.ssafy.glu.problem.domain.problem.dto.response.UserProblemLogResponse;
import com.ssafy.glu.problem.domain.problem.service.ProblemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/problems")
public class ProblemController {
	private final ProblemService problemService;

	@GetMapping("/solve")
	public ResponseEntity<Page<UserProblemLogResponse>> getProblemListInLog(
		@RequestHeader("X-User-Id") Long userId,
		@ModelAttribute UserProblemLogSearchCondition condition,
		Pageable pageable
	) {
		return ResponseEntity.ok(problemService.getProblemListByLog(userId, condition, pageable));
	}

	@PutMapping("/memo/{problemMemoId}")
	public ResponseEntity<ProblemMemoResponse> updateUserProblemMemo(@RequestHeader(USER_ID) Long userId,
		@PathVariable("problemMemoId") String problemMemoId,
		@RequestBody ProblemMemoUpdateRequest problemMemoUpdateRequest) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(problemService.updateProblemMemo(userId, problemMemoId, problemMemoUpdateRequest));
	}

	@PostMapping("/{problemId}/favorite")
	public ResponseEntity<Void> createUserProblemFavorite(@RequestHeader(USER_ID) Long userId,
		@PathVariable("problemId") String problemId) {
		problemService.createUserProblemFavorite(userId, problemId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/{problemId}/favorite")
	public ResponseEntity<Void> deleteUserProblemFavorite(@RequestHeader(USER_ID) Long userId,
		@PathVariable("problemId") String problemId) {
		problemService.deleteUserProblemFavorite(userId, problemId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}