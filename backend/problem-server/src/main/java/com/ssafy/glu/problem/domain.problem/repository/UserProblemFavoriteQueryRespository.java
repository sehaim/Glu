package com.ssafy.glu.problem.domain.problem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSearchCondition;

public interface UserProblemFavoriteQueryRespository {
	Page<Problem> findAllFavoriteProblem(Long userId, ProblemSearchCondition condition, Pageable pageable);
}
