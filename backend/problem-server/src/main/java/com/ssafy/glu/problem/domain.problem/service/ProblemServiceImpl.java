package com.ssafy.glu.problem.domain.problem.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.glu.problem.domain.problem.dto.request.UserProblemLogSearchCondition;
import com.ssafy.glu.problem.domain.problem.dto.response.UserProblemLogResponse;
import com.ssafy.glu.problem.domain.problem.repository.ProblemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {
	private final ProblemRepository problemRepository;

	@Override
	public List<UserProblemLogResponse> getUserProblemLogList(Long userId, UserProblemLogSearchCondition condition,
		Pageable pageable) {

		

		return List.of();
	}
}