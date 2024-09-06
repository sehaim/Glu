package com.ssafy.glu.problem.domain.problem.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.glu.problem.domain.problem.dto.request.UserProblemLogSearchCondition;
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
}