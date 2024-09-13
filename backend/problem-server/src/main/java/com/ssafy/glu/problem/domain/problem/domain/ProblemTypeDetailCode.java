package com.ssafy.glu.problem.domain.problem.domain;

import lombok.Getter;

@Getter
public enum ProblemTypeDetailCode {
	PT0111("옳게 설명하는 문장 고르기"),
	PT0112("문장의 해당 단어와 비슷한 / 반대되는 단어 고르기"),
	PT0121("어색한 문장 찾기 - 맞춤법이 올바른 문장 찾기");

	final String description;

	// 열거형 생성자에서 각기 다른 람다식을 받아 초기화
	ProblemTypeDetailCode(String description) {
		this.description = description;
	}
}