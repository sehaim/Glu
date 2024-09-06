package com.ssafy.glu.problem.domain.problem.service;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.glu.problem.domain.problem.dto.request.ProblemMemoUpdateRequest;
import com.ssafy.glu.problem.domain.problem.dto.request.UserProblemLogSearchCondition;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemMemoResponse;
import com.ssafy.glu.problem.domain.problem.dto.response.UserProblemLogResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Primary
public class ProblemValidationService implements ProblemService {
	private final ProblemService problemService;

	@Override
	public Page<UserProblemLogResponse> getProblemListByLog(Long userId, UserProblemLogSearchCondition condition,
		Pageable pageable) {
		return problemService.getProblemListByLog(userId, condition, pageable);
	}

	@Override
	public ProblemMemoResponse updateProblemMemo(Long userId, String problemMemoId, ProblemMemoUpdateRequest request) {
		return problemService.updateProblemMemo(userId,problemMemoId,request);
	}

	@Override
	public void createUserProblemFavorite(Long userId, String problemId) {
		problemService.createUserProblemFavorite(userId,problemId);
	}

	@Override
	public void deleteUserProblemFavorite(Long userId, String problemId) {
		problemService.deleteUserProblemFavorite(userId,problemId);
	}
}


