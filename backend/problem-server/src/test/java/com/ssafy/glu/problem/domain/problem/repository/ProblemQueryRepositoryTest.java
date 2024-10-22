package com.ssafy.glu.problem.domain.problem.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.ProblemLevelCode;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeCode;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeDetailCode;

import lombok.extern.slf4j.Slf4j;

@DataMongoTest
@ActiveProfiles("test")
@Slf4j
class ProblemQueryRepositoryTest {
	@Autowired
	private ProblemQueryRepositoryImpl problemRepository;

	@Test
	void conditionTest() {
		ProblemLevelCode problemLevelCode = null;
		ProblemTypeCode problemTypeCode = ProblemTypeCode.PT01;
		ProblemTypeDetailCode problemTypeDetailCode = ProblemTypeDetailCode.PT0111;

		assertThat(problemRepository.levelEq(problemLevelCode)).isNull();
		assertThat(problemRepository.typeEq(problemTypeCode)).isNotNull();
		assertThat(problemRepository.detailTypeEq(problemTypeDetailCode)).isNotNull();
	}
}