package com.ssafy.glu.problem.domain.problem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.ProblemMemo;

@Repository
public interface ProblemMemoRepository extends MongoRepository<ProblemMemo, String> {
	Page<ProblemMemo> findAllByProblemOrderByCreatedDateDesc(Problem problem, Pageable pageable);
}
