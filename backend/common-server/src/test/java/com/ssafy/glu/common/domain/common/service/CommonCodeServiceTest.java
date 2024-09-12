package com.ssafy.glu.common.domain.common.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import com.ssafy.glu.common.domain.common.domain.CommonCode;
import com.ssafy.glu.common.domain.common.repository.CommonCodeRepository;
import com.ssafy.glu.common.util.MockFactory;

import lombok.extern.slf4j.Slf4j;

@DataMongoTest
@ActiveProfiles("test")
@Slf4j
class CommonCodeServiceTest {
	@Autowired
	private CommonCodeRepository commonCodeRepository;

	@BeforeEach
	public void setUp() {
		commonCodeRepository.deleteAll();
	}

	@Test
	void getCommonCodeTest() {
		List<CommonCode> commonCodeList = MockFactory.createRandomCommonCodeList();

		assertNotNull(commonCodeList);
		assertTrue(commonCodeList.size() > 0);
		assertEquals("문제 유형", commonCodeList.get(0).getName());
	}
}
