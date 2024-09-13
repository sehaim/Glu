package com.ssafy.glu.problem.domain.problem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.glu.problem.domain.problem.domain.UserProblemStatus;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSearchCondition;

public interface UserProblemStatusQueryRepository {
	Page<UserProblemStatus> findAllProblemByCondition(Long userId, ProblemSearchCondition condition, Pageable pageable);
}