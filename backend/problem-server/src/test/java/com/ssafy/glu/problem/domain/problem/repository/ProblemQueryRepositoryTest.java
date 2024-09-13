package com.ssafy.glu.problem.domain.problem.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import lombok.extern.slf4j.Slf4j;

@DataMongoTest
@ActiveProfiles("test")
@Slf4j
class ProblemQueryRepositoryTest {
	@Autowired
	private ProblemQueryRepositoryImpl problemRepository;

	@Test
	void conditionTest() {
		String problemLevelCode = null;
		String problemTypeCode = "PT01";
		String problemTypeDetailCode = "PT0111";

		assertThat(problemRepository.levelEq(problemLevelCode)).isNull();
		assertThat(problemRepository.typeEq(problemTypeCode)).isNotNull();
		assertThat(problemRepository.detailTypeEq(problemTypeDetailCode)).isNotNull();
	}
}