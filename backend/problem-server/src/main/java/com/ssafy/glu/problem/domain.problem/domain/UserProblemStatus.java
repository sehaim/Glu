package com.ssafy.glu.problem.domain.problem.domain;

import java.util.List;

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
public class UserProblemStatus extends BaseTimeDocument {
	@Id
	private String userProblemStatusId;

	private Problem.Status status;

	private Integer attemptCount;

	private Integer wrongCount;

	private Long userId;

	private List<ProblemMemo> memoList;

	private Boolean isFavorite;

	@Field("problemId")
	@DocumentReference(lazy = true)
	private Problem problem;

	@Builder
	public UserProblemStatus(Problem.Status status, Integer attemptCount, Integer wrongCount, Long userId,
		List<ProblemMemo> memoList, Boolean isFavorite, Problem problem) {
		this.status = status;
		this.attemptCount = attemptCount;
		this.wrongCount = wrongCount;
		this.userId = userId;
		this.memoList = memoList;
		this.isFavorite = isFavorite;
		this.problem = problem;
	}

	public ProblemMemo addMemo(String content) {
		ProblemMemo memo = ProblemMemo.builder()
			.memoIndex(generateMemoIndex())
			.content(content)
			.build();
		memoList.add(memo);
		return memo;
	}

	// userProblemStatus에 있는 memolist 중에서 가장 큰 index 값보다 + 1 반환
	// 중복되지 않는 index 값을 할당하기 위함
	public Long generateMemoIndex() {
		// 해당 userId와 problemId에서 memoList가 비어 있으면 1을 반환하고, 그렇지 않으면 가장 큰 인덱스에 1을 더함
		return memoList.isEmpty() ? 1L :
			memoList.stream()
				.map(ProblemMemo::getMemoIndex)
				.max(Long::compareTo)
				.orElse(0L) + 1L;
	}
}
