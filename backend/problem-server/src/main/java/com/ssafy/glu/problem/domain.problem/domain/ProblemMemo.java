package com.ssafy.glu.problem.domain.problem.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class ProblemMemo {
	private final Long memoIndex;
	private String content;

	public void updateContent(String content) {
		this.content = content;
	}
}