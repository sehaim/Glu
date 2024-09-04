package com.ssafy.glu.problem.domain.problem.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ssafy.glu.problem.global.shared.BaseTimeDocument;

import lombok.Builder;
import lombok.Getter;

@Document
@Getter
public class Problem extends BaseTimeDocument {
	@Id
	private String problemId;

	private String title;

	private String content;

	private String solution;

	private ProblemLevel level;

	@Builder
	public Problem(String title, String content, String solution, ProblemLevel level) {
		this.title = title;
		this.content = content;
		this.solution = solution;
		this.level = level;
	}
}
