package com.ssafy.glu.problem.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.ProblemLevelCode;
import com.ssafy.glu.problem.domain.problem.domain.ProblemMemo;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeCode;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeDetailCode;
import com.ssafy.glu.problem.domain.problem.domain.QuestionTypeCode;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemLog;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemStatus;

public class MockFactory {

	private static final Random RANDOM = new Random();

	//===== Problem =====//
	public static Problem createProblem() {
		// 랜덤한 문자열 생성
		String randomTitle = "Title " + UUID.randomUUID().toString().substring(0, 8);
		String randomContent = "Content " + UUID.randomUUID().toString().substring(0, 8);
		String randomSolution = "Solution " + UUID.randomUUID().toString().substring(0, 8);

		Map<String, Object> metadata = new HashMap<>();

		metadata.put("options", List.of("option1", "option2", "option3"));
		metadata.put("imageUrl", "url");

		return Problem.builder()
			.title(randomTitle)
			.content(randomContent)
			.solution(randomSolution)
			.problemLevelCode(createProblemLevel())
			.problemTypeCode(createProblemType())
			.problemTypeDetailCode(createProblemTypeDetail())
			.problemTypeCode(createProblemType())
			.questionTypeCode(createQuestionType())
			.metadata(metadata)
			.build();
	}

	public static Problem createProblem(ProblemLevelCode problemLevelCode) {
		// 랜덤한 문자열 생성
		String randomTitle = "Title " + UUID.randomUUID().toString().substring(0, 8);
		String randomContent = "Content " + UUID.randomUUID().toString().substring(0, 8);
		String randomSolution = "Solution " + UUID.randomUUID().toString().substring(0, 8);

		Map<String, Object> metadata = new HashMap<>();

		metadata.put("options", List.of("option1", "option2", "option3"));
		metadata.put("imageUrl", "url");

		return Problem.builder()
			.title(randomTitle)
			.content(randomContent)
			.solution(randomSolution)
			.problemLevelCode(problemLevelCode)
			.problemTypeCode(createProblemType())
			.problemTypeDetailCode(createProblemTypeDetail())
			.questionTypeCode(createQuestionType())
			.metadata(metadata)
			.build();
	}

	public static ProblemLevelCode createProblemLevel() {
		// 랜덤한 ProblemLevel 객체 생성
		return ProblemLevelCode.values()[RANDOM.nextInt(QuestionTypeCode.values().length)];
	}

	public static ProblemTypeDetailCode createProblemTypeDetail() {
		return ProblemTypeDetailCode.values()[RANDOM.nextInt(QuestionTypeCode.values().length)];
	}

	public static ProblemTypeCode createProblemType() {
		return ProblemTypeCode.values()[RANDOM.nextInt(QuestionTypeCode.values().length)];
	}

	public static QuestionTypeCode createQuestionType() {
		// 랜덤한 ProblemTypeDetail 객체 생성
		return QuestionTypeCode.values()[RANDOM.nextInt(QuestionTypeCode.values().length)];
	}

	public static ProblemMemo createProblemMemo(Long memoIndex) {
		// 랜덤한 ProblemMemo 객체 생성
		String content = "내용 " + UUID.randomUUID().toString().substring(0, 8);
		return ProblemMemo.builder()
			.memoIndex(memoIndex)
			.content(content)
			.build();
	}

	//===== UserProblemLog =====//
	public static UserProblemLog createUserProblemLog(Long userId, Problem problem, boolean isCorrect) {
		// 랜덤한 문자열 생성
		String randomAnswer = "Answer " + UUID.randomUUID().toString().substring(0, 8);
		int randomSolveTime = RANDOM.nextInt(10);

		return UserProblemLog.builder()
			.userId(userId)
			.problem(problem)
			.userAnswer(randomAnswer)
			.isCorrect(isCorrect)
			.solvedTime(randomSolveTime)
			.build();
	}

	//===== UserProblemStatus =====//
	public static UserProblemStatus createUserProblemStatus(Long userId, Problem problem) {
		return new UserProblemStatus(
			Problem.Status.CORRECT, RANDOM.nextInt(5) + 5, RANDOM.nextInt(5), userId, new ArrayList<>(),
			RANDOM.nextBoolean(), problem);
	}
	public static UserProblemStatus createUserProblemStatus(Long userId, Problem.Status status, Problem problem, Map<Long, String> memoList, boolean isFavorite) {
		// 랜덤한 문자열 생성
		int randomAttemptCount = RANDOM.nextInt(1,5);
		int randomWrongCount = RANDOM.nextInt(randomAttemptCount);

		return UserProblemStatus.builder()
			.status(status)
			.attemptCount(randomAttemptCount)
			.wrongCount(randomWrongCount)
			.userId(userId)
			.memoList(memoList.entrySet().stream().map(entry->new ProblemMemo(entry.getKey(),entry.getValue())).toList())
			.isFavorite(isFavorite)
			.problem(problem)
			.build();
	}
}
