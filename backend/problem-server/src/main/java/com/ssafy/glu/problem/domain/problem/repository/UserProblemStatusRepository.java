package com.ssafy.glu.problem.domain.problem.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemStatus;

@Repository
public interface UserProblemStatusRepository extends MongoRepository<UserProblemStatus, String>,
	UserProblemStatusQueryRepository {
	// userId와 problemId로 UserProblemStatus를 찾는 메서드
	Optional<UserProblemStatus> findByUserIdAndProblem_ProblemId(Long userId, String problemId);

	Optional<UserProblemStatus> findByUserIdAndProblem(Long userId, Problem problem);

	@Query("{ 'userId': ?0, 'problem.problemId': ?1 }")
	Optional<UserProblemStatus> findByUserIdAndProblemId(Long userId, String problemId);
}
