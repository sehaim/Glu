package com.ssafy.glu.problem.domain.problem.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.glu.problem.domain.problem.domain.Problem;

@Repository
public interface ProblemRepository extends MongoRepository<Problem, String>, ProblemQueryRepository {
}
