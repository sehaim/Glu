package com.ssafy.glu.problem.domain.problem.domain;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ssafy.glu.problem.global.shared.BaseTimeDocument;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Document
@Getter
@ToString
public class Problem extends BaseTimeDocument {
	@Id
	private String problemId;

	private final QuestionType questionType;

	private final String title;

	private final String content;

	private final String solution;

	private final ProblemLevel problemLevel;

	private final ProblemTypeDetail problemTypeDetail;

	private final ProblemType problemType;

	private final Map<String, Object> metadata;

	@Builder
	public Problem(String title, String content, String solution, ProblemLevel problemLevel, ProblemType problemType,
		ProblemTypeDetail problemTypeDetail, QuestionType questionType, Map<String, Object> metadata) {
		this.title = title;
		this.content = content;
		this.solution = solution;
		this.problemLevel = problemLevel;
		this.problemType = problemType;
		this.problemTypeDetail = problemTypeDetail;
		this.questionType = questionType;
		this.metadata = metadata;
	}

	public enum Status {
		CORRECT,
		WRONG
	}
}
