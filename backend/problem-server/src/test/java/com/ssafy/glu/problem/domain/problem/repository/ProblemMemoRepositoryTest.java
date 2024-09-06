package com.ssafy.glu.problem.domain.problem.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	void createProblemMemoTest() {
		// Given
		Problem problem = problemRepository.save(MockFactory.createProblem());
		long beforeCreateProblemMemo = problemMemoRepository.count();

		ProblemMemo problemMemo = problemMemoRepository.save(MockFactory.createProblemMemo(1L, problem));

		// When
		problemMemoRepository.save(problemMemo);
		long afterCreateProblemMemo = problemMemoRepository.count();

		// Then
		assertThat(afterCreateProblemMemo - beforeCreateProblemMemo).isOne();
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
		long beforeDeletedCount = problemMemoRepository.count();

		// When
		problemMemoRepository.deleteById(problemMemo.getProblemMemoId());
		long afterDeletedCount = problemMemoRepository.count();

		// Then
		assertThat(beforeDeletedCount - afterDeletedCount).isOne();
	}

	@Test
	void findAllBuProblemTest() {
		// Given
		Problem problem = problemRepository.save(MockFactory.createProblem());

		ProblemMemo problemMemo1 = problemMemoRepository.save(MockFactory.createProblemMemo(1L, problem));
		ProblemMemo problemMemo2 = problemMemoRepository.save(MockFactory.createProblemMemo(1L, problem));
		ProblemMemo problemMemo3 = problemMemoRepository.save(MockFactory.createProblemMemo(1L, problem));
		ProblemMemo problemMemo4 = problemMemoRepository.save(MockFactory.createProblemMemo(1L, problem));

		// When
		Page<ProblemMemo> result = problemMemoRepository.findAllByProblemOrderByCreatedDateDesc(problem,
			Pageable.unpaged());

		// Then
		assertThat(result.getContent()).hasSize(4);
	}
}
