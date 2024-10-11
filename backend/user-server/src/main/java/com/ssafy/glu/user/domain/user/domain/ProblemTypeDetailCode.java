package com.ssafy.glu.user.domain.user.domain;

import lombok.Getter;

@Getter
public enum ProblemTypeDetailCode {
	/* PT01 */
	PT0111("옳게 설명하는 문장 고르기"),
	PT0112("문장의 해당 단어와 비슷한 / 반대되는 단어 고르기"),

	PT0121("어색한 문장 찾기 - 맞춤법이 올바른 문장 찾기"),
	PT0122("동사 형용사 구분하기"),
	PT0123("빈칸에 들어갈 알맞은 접속사 찾기"),
	PT0124("문장 구조에 맞는 어미 구분하기"),

	/* PT02 */
	PT0211("동화 내용 확인하기 - 주어진 글(동화)을 읽고 올바른 것S 옳지 않은 것 고르기"),

	PT0221("뉴스 기사의 핵심어 파악하기"),
	PT0222("뉴스 기사의 내용 파악하기 - 주장을 뒷받침하는 내용인 것 고르기"),

	/* PT03 */
	PT0311("그림을 보고 상황에 맞는 문장 고르기"),
	PT0312("동화를 읽고 내용 추론하기"),

	PT0321("뉴스 기사를 읽고 빈칸 추론하기");

	final String description;

	// 열거형 생성자에서 각기 다른 람다식을 받아 초기화
	ProblemTypeDetailCode(String description) {
		this.description = description;
	}
}