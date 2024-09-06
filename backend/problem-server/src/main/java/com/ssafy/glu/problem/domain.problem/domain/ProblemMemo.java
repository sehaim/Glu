package com.ssafy.glu.problem.domain.problem.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import com.ssafy.glu.problem.global.shared.BaseTimeDocument;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Document
@Getter
@ToString
public class ProblemMemo extends BaseTimeDocument {
	@Id
	private String problemMemoId;

	private String content;

	private final Long userId;

	@Field("problemId")
	@DocumentReference(lazy = true)
	private final Problem problem;

	@Builder
	public ProblemMemo(Long userId, Problem problem, String content) {
		this.userId = userId;
		this.problem = problem;
		this.content = content;
	}

	public void updateContent(String content) {
		if (content != null) {
			this.content = content;
		}
	}
}
