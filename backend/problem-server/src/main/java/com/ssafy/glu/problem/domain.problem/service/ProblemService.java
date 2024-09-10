package com.ssafy.glu.problem.domain.problem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoCreateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoUpdateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSearchCondition;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemBaseResponse;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemMemoResponse;

public interface ProblemService {
	Page<ProblemBaseResponse> getProblemList(Long userId, ProblemSearchCondition condition, Pageable pageable);
	ProblemMemoResponse createProblemMemo(Long userId, String problemId, ProblemMemoCreateRequest request);
	ProblemMemoResponse updateProblemMemo(Long userId, String problemId, ProblemMemoUpdateRequest request);
	void deleteProblemMemo(Long userId, String problemId, Long memoIndex);
	Page<ProblemMemoResponse> getProblemMemoList(Long userId, String problemId, Pageable pageable);
	void createUserProblemFavorite(Long userId, String problemId);
	void deleteUserProblemFavorite(Long userId, String problemId);

	// TestGradingDetailResponse gradeProblemList(Long userId, ProblemSolveListRequest request);
	// ProblemGradingResponse gradeProblem(Long userId, String problemId, ProblemSolveRequest request);
	// TestGradingBaseResponse getPreviousTest(Long userId);
	// List<TestGradingDetailResponse> getTestList(Long userId, Pageable pageable);
}