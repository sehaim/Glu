package com.ssafy.glu.problem.domain.problem.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemFavorite;

@Repository
public interface UserProblemFavoriteRepository extends MongoRepository<UserProblemFavorite, String>, UserProblemFavoriteQueryRespository {
	boolean existsByUserIdAndProblem(Long userId, Problem problem);

	void deleteByUserIdAndProblem(Long userId, Problem problem);
}
