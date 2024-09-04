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

	private QuestionType questionType;

	private String title;

	private String content;

	private String solution;

	private ProblemLevel level;

	private ProblemTypeDetail problemType;

	private Map<String,Object> metadata;

	@Builder
	public Problem(String title, String content, String solution, ProblemLevel level, ProblemTypeDetail problemType, QuestionType questionType, Map<String, Object> metadata) {
		this.title = title;
		this.content = content;
		this.solution = solution;
		this.level = level;
		this.problemType = problemType;
		this.questionType = questionType;
		this.metadata = metadata;
	}

	public enum Status{
		CORRECT,
		WRONG
	}
}
