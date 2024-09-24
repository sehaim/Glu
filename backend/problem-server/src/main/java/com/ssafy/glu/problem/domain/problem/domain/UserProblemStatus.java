package com.ssafy.glu.problem.domain.problem.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ssafy.glu.problem.domain.problem.exception.favorite.FavoriteAlreadyRegisteredException;
import com.ssafy.glu.problem.domain.problem.exception.favorite.FavoriteNotRegisteredException;
import com.ssafy.glu.problem.domain.problem.exception.memo.ProblemMemoNotFoundException;
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

	//== querydsl 조회를 위한 embedding 처리, 성능 개선 ==//
	// @Field("problemId")
	// @DocumentReference(lazy = true)
	private Problem problem;

	@Builder
	public UserProblemStatus(Problem.Status status, Integer attemptCount, Integer wrongCount, Long userId,
		List<ProblemMemo> memoList, Boolean isFavorite, Problem problem) {
		this.status = status;
		this.attemptCount = attemptCount != null ? attemptCount : 0;
		this.wrongCount = wrongCount != null ? wrongCount : 0;
		this.userId = userId;
		this.memoList = memoList != null ? memoList : new ArrayList<>();
		this.isFavorite = isFavorite != null ? isFavorite : false;
		this.problem = problem;
	}

	//=== 비즈니스 로직 ===//
	// 문제 풀이시 데이터 업데이트
	public void updateWhenSolve(boolean isCorrect) {
		increaseAttemptCount();
		if (isCorrect) {
			updateStatus(Problem.Status.CORRECT);
		} else {
			updateStatus(Problem.Status.WRONG);
			increaseWrongCount();
		}
	}

	// 풀이 상태 업데이트
	public void updateStatus(Problem.Status status) {
		if (status != null)
			this.status = status;
	}

	// 시도 횟수 증가
	public void increaseAttemptCount() {
		this.attemptCount++;
	}

	// 틀린 횟수 증가
	public void increaseWrongCount() {
		this.wrongCount++;
	}

	// 메모 추가
	public ProblemMemo addMemo(String content) {
		ProblemMemo memo = ProblemMemo.builder()
			.memoIndex(generateMemoIndex())
			.content(content)
			.build();
		memoList.add(memo);
		return memo;
	}

	// 메모 업데이트
	public ProblemMemo updateMemo(Long memoIndex, String content) {
		return memoList.stream()
			.filter(memo -> memo.getMemoIndex().equals(memoIndex))
			.findFirst()
			.map(memo -> {
				memo.updateContent(content);
				return memo;
			})
			.orElseThrow(ProblemMemoNotFoundException::new);
	}

	// 메모 삭제
	public void deleteMemo(Long memoIndex) {
		boolean result = memoList.removeIf(memo -> memo.getMemoIndex().equals(memoIndex));
		if (!result) {
			throw new ProblemMemoNotFoundException();
		}
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

	// 찜 등록
	public void createFavorite() {
		// 이미 찜이 된 상태
		if (isFavorite)
			throw new FavoriteAlreadyRegisteredException();
		this.isFavorite = true;
	}

	// 찜 취소
	public void deleteFavorite() {
		// 취소할 찜이 없는 상태
		if (!isFavorite)
			throw new FavoriteNotRegisteredException();
		this.isFavorite = false;
	}
}
