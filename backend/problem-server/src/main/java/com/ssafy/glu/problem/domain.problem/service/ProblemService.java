package com.ssafy.glu.problem.domain.problem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.glu.problem.domain.problem.dto.request.UserProblemLogSearchCondition;
import com.ssafy.glu.problem.domain.problem.dto.response.UserProblemLogResponse;

public interface ProblemService {
	Page<UserProblemLogResponse> getProblemListByLog(Long userId, UserProblemLogSearchCondition condition, Pageable pageable);
	// ProblemMemoResponse createProblemMemo(Long userId, String problemId, ProblemMemoCreateRequest request);
	// ProblemMemoResponse updateProblemMemo(String problemMemoId, ProblemMemoUpdateRequest request);
	// void deleteMemo(String problemMemoId);
	// List<ProblemMemoResponse> getProblemMemoList(Long userId, String problemId, Pageable pageable);
	// List<ProblemBaseResponse> getUserProblemFavoriteList(Long userId, Pageable pageable);
	// void createUserProblemFavorite(Long userId, String problemId);
	// void deleteUserProblemFavorite(Long userId, String problemId);
	// TestGradingDetailResponse gradeProblemList(Long userId, ProblemSolveListRequest request);
	// ProblemGradingResponse gradeProblem(Long userId, String problemId, ProblemSolveRequest request);
	// TestGradingBaseResponse getPreviousTest(Long userId);
	// List<TestGradingDetailResponse> getTestList(Long userId, Pageable pageable);
}