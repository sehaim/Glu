package com.ssafy.glu.problem.domain.problem.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSearchCondition;

public interface UserProblemLogQueryRepository {
    Page<Problem> findAllProblemInLogByCondition(Long userId, ProblemSearchCondition condition, Pageable pageable);

    Optional<UserProblemLog> findFirstByUserIdAndProblem(Long userId, Problem problem);
}