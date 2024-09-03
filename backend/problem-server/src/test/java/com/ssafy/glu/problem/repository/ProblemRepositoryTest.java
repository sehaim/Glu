package com.ssafy.glu.problem.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.glu.problem.MockFactory;
import com.ssafy.glu.problem.domain.problem.domain.Problem;

@SpringBootTest
@Transactional
@Profile("test")
class ProblemRepositoryTest {
	@Autowired
	private ProblemRepository problemRepository;

	@Test
	void problemSaveTest() {
		Problem problem = MockFactory.createProblem();

		Problem savedProblem = problemRepository.save(problem);

		List<Problem> problemList = problemRepository.findAll();

		// 문제 수 체크
		assertThat(problemList.size()).isEqualTo(1);

		// 문제 정보 확인
		assertThat(savedProblem.getTitle()).isEqualTo(problem.getTitle());
		assertThat(savedProblem.getContent()).isEqualTo(problem.getContent());
		assertThat(savedProblem.getSolution()).isEqualTo(problem.getSolution());
		assertThat(savedProblem.getLevel().getProblemLevelCode()).isEqualTo(problem.getLevel().getProblemLevelCode());
	}
}