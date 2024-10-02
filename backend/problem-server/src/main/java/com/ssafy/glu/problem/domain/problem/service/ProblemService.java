package com.ssafy.glu.problem.domain.problem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoCreateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoUpdateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSearchCondition;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSolveRequest;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemBaseResponse;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemGradingResponse;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemMemoResponse;

public interface ProblemService {
	ProblemBaseResponse getProblem(String problemId);

	Boolean getIsFavorite(Long userId, String problemId);

	Page<ProblemBaseResponse> getProblemList(Long userId, ProblemSearchCondition condition, Pageable pageable);

	ProblemMemoResponse createProblemMemo(Long userId, String problemId, ProblemMemoCreateRequest request);

	ProblemMemoResponse updateProblemMemo(Long userId, String problemId, ProblemMemoUpdateRequest request);

	void deleteProblemMemo(Long userId, String problemId, Long memoIndex);

	Page<ProblemMemoResponse> getProblemMemoList(Long userId, String problemId, Pageable pageable);

	void createUserProblemFavorite(Long userId, String problemId);

	void deleteUserProblemFavorite(Long userId, String problemId);

	ProblemGradingResponse gradeProblem(Long userId, String problemId, ProblemSolveRequest request);
}