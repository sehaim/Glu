package com.ssafy.glu.problem.domain.problem.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.ProblemMemo;
import com.ssafy.glu.problem.domain.problem.exception.ProblemMemoNotFoundException;
import com.ssafy.glu.problem.util.MockFactory;

import lombok.extern.slf4j.Slf4j;

@DataMongoTest
@ActiveProfiles("test")
@Slf4j
class ProblemMemoRepositoryTest {
	@Autowired
	private ProblemMemoRepository problemMemoRepository;

	@Autowired
	private ProblemRepository problemRepository;

	@BeforeEach
	public void setUp() {
		problemMemoRepository.deleteAll();
		problemRepository.deleteAll();
	}

	@Test
	void updateProblemMemoContentTest() {
		// Given
		Problem problem = problemRepository.save(MockFactory.createProblem());
		ProblemMemo problemMemo = problemMemoRepository.save(MockFactory.createProblemMemo(1L, problem));
		ProblemMemo savedProblemMemo = problemMemoRepository.save(problemMemo);
		String updatedContent = "Updated Content " + UUID.randomUUID().toString().substring(0, 8);

		// When
		savedProblemMemo.updateContent(updatedContent);
		problemMemoRepository.save(savedProblemMemo);

		// Then
		ProblemMemo updatedProblemMemo = problemMemoRepository.findById(savedProblemMemo.getProblemMemoId())
			.orElseThrow(
				ProblemMemoNotFoundException::new);
		assertThat(updatedProblemMemo.getContent()).isEqualTo(updatedContent);
	}

	@Test
	void deleteProblemMemoTest() {
		// Given
		Problem problem = problemRepository.save(MockFactory.createProblem());
		ProblemMemo problemMemo = problemMemoRepository.save(MockFactory.createProblemMemo(1L, problem));

		// When
		problemMemoRepository.deleteById(problemMemo.getProblemMemoId());

		// Then
		assertThat(problemMemoRepository.count()).isZero();
	}
}
