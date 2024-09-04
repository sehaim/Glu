package com.ssafy.glu.problem.domain.problem.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ssafy.glu.problem.global.shared.BaseTimeDocument;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Document
@Getter
@ToString
public class UserProblemLog extends BaseTimeDocument {
	@Id
	private String userProblemLogId;

	private final boolean isCorrect;

	private final String userAnswer;

	private final Integer solvedTime;

	private final Long userId;

	private final String problemId;

	@Builder
	public UserProblemLog(boolean isCorrect, String userAnswer, Integer solvedTime, Long userId, String problemId) {
		this.isCorrect = isCorrect;
		this.userAnswer = userAnswer;
		this.solvedTime = solvedTime;
		this.userId = userId;
		this.problemId = problemId;
	}
}
