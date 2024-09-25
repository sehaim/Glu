package com.ssafy.glu.problem.domain.test.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ssafy.glu.problem.global.shared.BaseTimeDocument;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Document
@Getter
@ToString
public class Test extends BaseTimeDocument {
	@Id
	private String testId;

	private final Integer correctCount;

	private final Integer totalSolveTime;

	private final Long userId;

	private final List<String> userProblemLogIdList;

	@Builder
	public Test(Integer correctCount, Integer totalSolveTime, Long userId, List<String> userProblemLogIdList) {
		this.correctCount = correctCount;
		this.totalSolveTime = totalSolveTime;
		this.userId = userId;
		this.userProblemLogIdList = userProblemLogIdList;
	}
}
