package com.ssafy.glu.problem.domain.problem.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Document
@Getter
@ToString
public class ProblemMemo {
	@Id
	private String problemMemoId;

	private String contents;

	private final Long userId;

	@Field("problemId")
	@DocumentReference(lazy = true)
	private final Problem problem;

	@Builder
	public ProblemMemo(Long userId, Problem problem, String contents) {
		this.userId = userId;
		this.problem = problem;
		this.contents = contents;
	}

	public void updateContents(String contents) {
		if (contents != null) {
			this.contents = contents;
		}
	}
}
