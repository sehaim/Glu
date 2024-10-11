package com.ssafy.glu.common.util;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.ssafy.glu.common.domain.common.domain.CommonCode;

public class MockFactory {

	private static final Random RANDOM = new Random();

	public static CommonCode createCommonCode(String typeCode, String name, CommonCode parentCode) {
		return CommonCode.builder()
			.typeCode(typeCode)
			.name(name)
			.parentCode(parentCode)
			.build();
	}

	public static CommonCode createRandomCommonCode() {
		String typeCode = "CODE_" + UUID.randomUUID().toString().substring(0, 8);
		String name = "NAME_" + UUID.randomUUID().toString().substring(0, 8);
		return createCommonCode(typeCode, name, null);
	}

	public static CommonCode createRandomCommonCode(CommonCode parentCode) {
		String typeCode = "CODE_" + UUID.randomUUID().toString().substring(0, 8);
		String name = "NAME_" + UUID.randomUUID().toString().substring(0, 8);
		return createCommonCode(typeCode, name, parentCode);
	}

	public static List<CommonCode> createRandomCommonCodeList() {
		return List.of(
			createCommonCode("PT", "문제 유형", null),
			createCommonCode("PL", "문제 레벨", null),
			createCommonCode("QT", "질문 유형", null)
		);
	}

	public static List<CommonCode> createRandomCommonCodeDetailList() {
		return List.of(
			createCommonCode("PT01", "어휘 및 문법", createCommonCode("PT", "문제 유형", null)),
			createCommonCode("PT02", "독해", createCommonCode("PT", "문제 유형", null)),
			createCommonCode("PT03", "추론", createCommonCode("PT", "문제 유형", null)),
			createCommonCode("PL01", "유아", createCommonCode("PL", "문제 레벨", null)),
			createCommonCode("PL02", "초등 1학년", createCommonCode("PL", "문제 레벨", null)),
			createCommonCode("QT01", "객관식", createCommonCode("QT", "질문 유형", null)),
			createCommonCode("QT02", "단답식", createCommonCode("QT", "질문 유형", null))
		);
	}
}

