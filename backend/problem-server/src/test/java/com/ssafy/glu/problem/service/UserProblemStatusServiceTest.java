package com.ssafy.glu.problem.service;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemStatus;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemMemoResponse;
import com.ssafy.glu.problem.domain.problem.exception.status.UserProblemStatusNotFoundException;
import com.ssafy.glu.problem.domain.problem.repository.ProblemRepository;
import com.ssafy.glu.problem.domain.problem.repository.UserProblemStatusRepository;
import com.ssafy.glu.problem.domain.problem.service.ProblemService;
import com.ssafy.glu.problem.domain.problem.service.ProblemServiceImpl;
import com.ssafy.glu.problem.util.MockFactory;

@DataMongoTest
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@ContextConfiguration(classes = {ProblemServiceImpl.class, ProblemRepository.class, UserProblemStatusRepository.class})
@EnableMongoRepositories(basePackages = "com.ssafy.glu.problem.domain.problem.repository")
public class UserProblemStatusServiceTest {

	@Autowired
	private ProblemService problemService;

	@Autowired
	private ProblemRepository problemRepository;

	@Autowired
	private UserProblemStatusRepository userProblemStatusRepository;

	private List<Problem> problemList;
	private final Long[] userIdList = {1L, 2L, 3L, 4L};

	@BeforeEach
	public void setUp() {
		problemRepository.deleteAll();
		userProblemStatusRepository.deleteAll();

		problemList = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			Problem problem = problemRepository.save(MockFactory.createProblem());
			problemList.add(problem);
		}

		for (Long userId : userIdList) {
			userProblemStatusRepository.save(
				MockFactory.createUserProblemStatus(userId, Problem.Status.CORRECT, problemList.get(0),
					Map.of(1L, "메모1", 2L, "메모2"), false));
			userProblemStatusRepository.save(
				MockFactory.createUserProblemStatus(userId, Problem.Status.WRONG, problemList.get(1), Map.of(1L, "메모1"),
					true));
			userProblemStatusRepository.save(
				MockFactory.createUserProblemStatus(userId, Problem.Status.WRONG, problemList.get(2), Map.of(), true));
		}
	}

	@Test
	void getProblemMemoList() {
		// Given
		Long userId = userIdList[0];
		Problem problem = problemList.get(0);

		// When
		Pageable pageable = PageRequest.of(0, 4);
		Page<ProblemMemoResponse> pagedMemo = problemService.getProblemMemoList(userId, problem.getProblemId(),
			pageable);

		// Then
		assertThat(pagedMemo.getTotalElements()).isEqualTo(2);
		assertThat(pagedMemo.getTotalPages()).isEqualTo(1);
		assertThat(pagedMemo.getNumberOfElements()).isEqualTo(2);
		assertThat(pagedMemo.getContent().get(0).content()).isEqualTo("메모1");
	}

	@Test
	void createFavoriteTest() {
		// Given
		Long userId = userIdList[0];
		Problem problem = problemList.get(0);

		// When
		UserProblemStatus userProblemStatus = userProblemStatusRepository.findByUserIdAndProblem_ProblemId(userId,
			problem.getProblemId()).orElseThrow(
			UserProblemStatusNotFoundException::new);
		userProblemStatus.createFavorite();
		userProblemStatusRepository.save(userProblemStatus);

		// Then
		assertThat(userProblemStatus.getIsFavorite()).isEqualTo(true);
	}

	@Test
	void deleteFavoriteTest() {
		// Given
		Long userId = userIdList[0];
		Problem problem = problemList.get(0);

		// When
		UserProblemStatus userProblemStatus = userProblemStatusRepository.findByUserIdAndProblem_ProblemId(userId,
			problem.getProblemId()).orElseThrow(
			UserProblemStatusNotFoundException::new);
		userProblemStatus.createFavorite();
		userProblemStatusRepository.save(userProblemStatus);

		boolean beforeDelete = userProblemStatus.getIsFavorite();
		userProblemStatus.deleteFavorite();
		userProblemStatusRepository.save(userProblemStatus);
		boolean afterDelete = userProblemStatus.getIsFavorite();

		// Then
		assertThat(beforeDelete).isEqualTo(true);
		assertThat(afterDelete).isEqualTo(false);

	}
}