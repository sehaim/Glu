package com.ssafy.glu.user.domain.user.domain;

import com.ssafy.glu.user.global.util.GradingStrategy;

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
