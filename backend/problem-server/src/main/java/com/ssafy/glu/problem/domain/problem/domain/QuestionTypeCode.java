package com.ssafy.glu.problem.domain.problem.domain;

import com.ssafy.glu.problem.global.util.GradingStrategy;

import lombok.Getter;

@Getter
public enum QuestionTypeCode {
	QT01("객관식", String::equals),
	QT02("단답식", String::equalsIgnoreCase),
	QT03("서술형", String::contains);

	final String description;
	final GradingStrategy gradingStrategy;

	// 열거형 생성자에서 각기 다른 람다식을 받아 초기화
	QuestionTypeCode(String description, GradingStrategy gradingStrategy) {
		this.description = description;
		this.gradingStrategy = gradingStrategy;
	}
}