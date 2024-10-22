package com.ssafy.glu.problem.domain.problem.domain;

import lombok.Getter;

@Getter
public enum ProblemTypeDetailCode {
	/* PT01 */
	PT0111("옳게 설명하는 문장 고르기"),
	PT0112("단어의 유의/반의어 고르기"),

	PT0121("문맥상 올바른 맞춤법 고르기"),

	/* PT02 */
	PT0211("명시적 내용 확인하기"),

	PT0221("뉴스 핵심어 파악하기"),
	PT0222("뉴스 내용 파악하기"),

	/* PT03 */
	PT0311("문장과 비슷한 그림 추론하기"),
	PT0312("함축적 내용 추론하기"),

	PT0321("뉴스 빈칸 추론하기");

	final String description;

	// 열거형 생성자에서 각기 다른 람다식을 받아 초기화
	ProblemTypeDetailCode(String description) {
		this.description = description;
	}
}