package com.ssafy.glu.problem.domain.problem.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSearchCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.ssafy.glu.problem.util.MockFactory;
import com.ssafy.glu.problem.domain.problem.domain.Problem;

import lombok.extern.slf4j.Slf4j;

@DataMongoTest
@ActiveProfiles("test")
@Slf4j
class ProblemRepositoryTest {
	@Autowired
	private ProblemRepository problemRepository;

	@BeforeEach
	public void setUp() {
		problemRepository.deleteAll();
	}

	@Test
	void saveProblemTest() {
		Problem problem = MockFactory.createProblem();

		Problem savedProblem = problemRepository.save(problem);

		log.info("Saved problem: {}", savedProblem);

		List<Problem> problemList = problemRepository.findAll();

		// 문제 수 체크
		assertThat(problemList.size()).isEqualTo(1);

		// 문제 정보 확인
		assertThat(savedProblem.getTitle()).isEqualTo(problem.getTitle());
		assertThat(savedProblem.getContent()).isEqualTo(problem.getContent());
		assertThat(savedProblem.getSolution()).isEqualTo(problem.getSolution());
		assertThat(savedProblem.getLevel().getProblemLevelCode()).isEqualTo(problem.getLevel().getProblemLevelCode());
	}

	@Test
	void getProblemTest() {
		Problem problem = MockFactory.createProblem();
		problemRepository.save(problem);

		Problem foundProblem = problemRepository.findById(problem.getProblemId()).orElseThrow();

		log.info("Found problem: {}", foundProblem);

		assertThat(foundProblem.getProblemId()).isEqualTo(problem.getProblemId());
	}

	@Test
	void searchProblemTest() {
		String problemLevelCode1 = "PL01";
		String problemLevelCode2 = "PL02";
		String problemLevelCode3 = "PL03";

		int numLevel1 = 2;
		int numLevel2 = 7;
		int numLevel3 = 4;

		for (int i = 0; i < numLevel1; i++) problemRepository.save(MockFactory.createProblem(problemLevelCode1));
		for (int i = 0; i < numLevel2; i++) problemRepository.save(MockFactory.createProblem(problemLevelCode2));
		for (int i = 0; i < numLevel3; i++) problemRepository.save(MockFactory.createProblem(problemLevelCode3));

		ProblemSearchCondition condition = ProblemSearchCondition.builder()
				.problemLevelCode(problemLevelCode2)
				.build();

		Page<Problem> problemList = problemRepository.findByCondition(condition, Pageable.ofSize(10));

		assertThat(problemList.getTotalElements()).isEqualTo(numLevel2);
	}
}