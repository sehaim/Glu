package com.ssafy.glu.problem.domain.problem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.glu.problem.domain.problem.dto.request.UserProblemLogSearchCondition;
import com.ssafy.glu.problem.domain.problem.dto.response.UserProblemLogResponse;
import com.ssafy.glu.problem.domain.problem.repository.ProblemRepository;
import com.ssafy.glu.problem.domain.problem.repository.UserProblemLogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProblemServiceImpl implements ProblemService {
	private final ProblemRepository problemRepository;
	private final UserProblemLogRepository userProblemLogRepository;

	@Override
	public Page<UserProblemLogResponse> getProblemListByLog(Long userId, UserProblemLogSearchCondition condition,
		Pageable pageable) {
		return userProblemLogRepository.findAllProblemInLogByCondition(userId,condition,pageable).map(problem->UserProblemLogResponse.of(problem,condition.status()));
	}
}