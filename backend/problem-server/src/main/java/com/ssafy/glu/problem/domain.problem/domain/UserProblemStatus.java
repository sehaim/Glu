package com.ssafy.glu.problem.domain.problem.domain;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import com.ssafy.glu.problem.global.shared.BaseTimeDocument;

import lombok.Getter;
import lombok.ToString;

@Document
@Getter
@ToString
public class UserProblemStatus extends BaseTimeDocument {
	@Id
	private String userProblemStatusId;

	private Problem.Status status;

	private Integer attemptCount;

	private Integer wrongCount;

	private Long userId;

	private Map<Long, String> memoList;

	private Boolean isFavorite;

	@Field("problemId")
	@DocumentReference(lazy = true)
	private Problem problem;

	public UserProblemStatus(Problem.Status status, Integer attemptCount, Integer wrongCount, Long userId,
		Map<Long, String> memoList, Boolean isFavorite, Problem problem) {
		this.status = status;
		this.attemptCount = attemptCount;
		this.wrongCount = wrongCount;
		this.userId = userId;
		this.memoList = memoList;
		this.isFavorite = isFavorite;
		this.problem = problem;
	}
}
