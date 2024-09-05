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
public class UserProblemFavorite {
	@Id
	private String userProblemFavoriteId;

	private final Long userId;

	@Field("problemId")
	@DocumentReference(lazy = true)
	private final Problem problem;

	@Builder
	public UserProblemFavorite(Long userId, Problem problem) {
		this.userId = userId;
		this.problem = problem;
	}
}
