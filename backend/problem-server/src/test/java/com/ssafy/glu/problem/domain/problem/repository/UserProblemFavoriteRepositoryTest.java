package com.ssafy.glu.problem.domain.problem.repository;

import static org.assertj.core.api.AssertionsForInterfaceTypes.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemFavorite;
import com.ssafy.glu.problem.util.MockFactory;

import lombok.extern.slf4j.Slf4j;

@DataMongoTest
@ActiveProfiles("test")
@Slf4j
class UserProblemFavoriteRepositoryTest {
	@Autowired
	private UserProblemFavoriteRepository userProblemFavoriteRepository;

	@Autowired
	private ProblemRepository problemRepository;

	@BeforeEach
	void setUp() {
		userProblemFavoriteRepository.deleteAll();
		problemRepository.deleteAll();
	}

	@Test
	void saveFavoriteTest() {
		// Given
		Problem problem = problemRepository.save(MockFactory.createProblem());
		UserProblemFavorite userProblemFavorite = UserProblemFavorite.builder()
			.userId(1L)
			.problem(problem)
			.build();

		// When
		UserProblemFavorite savedUserProblemFavorite = userProblemFavoriteRepository.save(userProblemFavorite);
		log.info("===== 문제 찜 저장 완료 : {} =====", savedUserProblemFavorite);

		// Then
		List<UserProblemFavorite> userProblemFavoriteList = userProblemFavoriteRepository.findAll();
		assertThat(userProblemFavoriteList).hasSize(1);

		UserProblemFavorite retrievedUserProblemFavorite = userProblemFavoriteList.get(0);
		assertThat(retrievedUserProblemFavorite.getUserId()).isEqualTo(userProblemFavorite.getUserId());
		assertThat(retrievedUserProblemFavorite.getProblem().getProblemId()).isEqualTo(problem.getProblemId());
	}

	@Test
	void existsByUserIdAndProblemTest() {
		// Given
		Problem problem = problemRepository.save(MockFactory.createProblem());
		UserProblemFavorite userProblemFavorite = UserProblemFavorite.builder()
			.userId(1L)
			.problem(problem)
			.build();
		userProblemFavoriteRepository.save(userProblemFavorite);

		// When
		boolean exists = userProblemFavoriteRepository.existsByUserIdAndProblem(1L, problem);

		// Then
		assertThat(exists).isTrue();
		log.info("===== 문제 찜 존재 여부 확인 UserId : {}, Problem : {} =====", 1L, problem);
	}
}
