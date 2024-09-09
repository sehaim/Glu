package com.ssafy.glu.problem.domain.problem.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForInterfaceTypes.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@Autowired
	private UserProblemLogRepository userProblemLogRepository;

	private final int NUM_PROBLEMS = 3;
	private List<Problem> problemList;
	private final Long[] userIdList = {1L, 2L, 3L, 4L};
	private final int NUM_LOGS_PER_USER = 3;

	@BeforeEach
	void setUp() throws InterruptedException {
		userProblemFavoriteRepository.deleteAll();
		problemRepository.deleteAll();

		problemList = new ArrayList<>();
		for (int i = 0; i < NUM_PROBLEMS; i++) {
			problemList.add(problemRepository.save(MockFactory.createProblem()));
		}

		// 유저별 풀이 기록 등록
		for (Long userId : userIdList) {
			for (Problem problem : problemList) {
				userProblemLogRepository.save(MockFactory.createUserProblemLog(userId, problem, false));
				Thread.sleep(1L);
				userProblemLogRepository.save(MockFactory.createUserProblemLog(userId, problem, false));
				Thread.sleep(1L);
				userProblemFavoriteRepository.save(
					UserProblemFavorite.builder().userId(userId).problem(problem).build());
				userProblemLogRepository.save(MockFactory.createUserProblemLog(userId, problem, true));
			}
		}
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

	@Test
	void deleteByUserIdAndProblemTest() {
		// Given
		Problem problem = problemRepository.save(MockFactory.createProblem());
		UserProblemFavorite userProblemFavorite = UserProblemFavorite.builder()
			.userId(1L)
			.problem(problem)
			.build();
		userProblemFavoriteRepository.save(userProblemFavorite);

		// When
		List<UserProblemFavorite> userProblemFavoriteList = userProblemFavoriteRepository.findAll();

		userProblemFavoriteRepository.deleteByUserIdAndProblem(1L, problem);
		boolean existsAfterDelete = userProblemFavoriteRepository.existsByUserIdAndProblem(1L, problem);

		// Then
		assertThat(userProblemFavoriteList).hasSize(1);
		assertThat(existsAfterDelete).isFalse();
		log.info("===== 문제 찜 취소 확인 UserId : {}, Problem : {} =====", 1L, problem);
	}

	@Test
	void searchUserProblemFavoriteTest() {
		Long userId = userIdList[0];

		Page<Problem> problemList = userProblemFavoriteRepository.findAllFavoriteProblem(userId, Pageable.ofSize(10));

		assertThat(problemList.getTotalElements()).isEqualTo(3);
	}
}
