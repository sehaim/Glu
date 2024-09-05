package com.ssafy.glu.problem.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.ProblemLevel;
import com.ssafy.glu.problem.domain.problem.domain.ProblemType;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeDetail;
import com.ssafy.glu.problem.domain.problem.domain.QuestionType;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemLog;

public class MockFactory {

	private static final Random RANDOM = new Random();

	//===== Problem =====//
	public static Problem createProblem() {
		// 랜덤한 문자열 생성
		String randomTitle = "Title " + UUID.randomUUID().toString().substring(0, 8);
		String randomContent = "Content " + UUID.randomUUID().toString().substring(0, 8);
		String randomSolution = "Solution " + UUID.randomUUID().toString().substring(0, 8);

		Map<String,Object> metadata = new HashMap<>();

		metadata.put("options", List.of("option1", "option2", "option3"));
		metadata.put("imageUrl", "url");

		return Problem.builder()
			.title(randomTitle)
			.content(randomContent)
			.solution(randomSolution)
			.level(createProblemLevel())
			.problemType(createProblemTypeDetail())
			.questionType(createQuestionType())
			.metadata(metadata)
			.build();
	}

	public static Problem createProblem(String problemLevelCode) {
		// 랜덤한 문자열 생성
		String randomTitle = "Title " + UUID.randomUUID().toString().substring(0, 8);
		String randomContent = "Content " + UUID.randomUUID().toString().substring(0, 8);
		String randomSolution = "Solution " + UUID.randomUUID().toString().substring(0, 8);

		Map<String,Object> metadata = new HashMap<>();

		metadata.put("options", List.of("option1", "option2", "option3"));
		metadata.put("imageUrl", "url");

		return Problem.builder()
				.title(randomTitle)
				.content(randomContent)
				.solution(randomSolution)
				.level(createProblemLevel(problemLevelCode))
				.problemType(createProblemTypeDetail())
				.questionType(createQuestionType())
				.metadata(metadata)
				.build();
	}

	public static ProblemLevel createProblemLevel() {
		// 랜덤한 ProblemLevel 객체 생성
		String level = "0"+RANDOM.nextInt(3);
		String code = "PL" + level;
		String name = "LV " + level;
		return ProblemLevel.builder()
			.problemLevelCode(code)
			.name(name)
			.build();
	}

	public static ProblemLevel createProblemLevel(String problemCodeLevelCode) {
		// 랜덤한 ProblemLevel 객체 생성
		String level = "0"+RANDOM.nextInt(3);
		String name = "LV " + level;
		return ProblemLevel.builder()
				.problemLevelCode(problemCodeLevelCode)
				.name(name)
				.build();
	}

	public static ProblemTypeDetail createProblemTypeDetail() {
		// 랜덤한 ProblemTypeDetail 객체 생성
		String num = "0"+RANDOM.nextInt(3);
		String problemTypeDetailCode = "0"+RANDOM.nextInt(3);
		String name = "유형 " + num;
		return ProblemTypeDetail.builder()
			.problemTypeDetailCode(problemTypeDetailCode)
			.problemType(createProblemType())
			.name(name)
			.build();
	}

	public static ProblemType createProblemType() {
		// 랜덤한 ProblemTypeDetail 객체 생성
		String num = "0"+RANDOM.nextInt(3);
		String problemTypeCode = "PB" + num;
		String name = "유형 " + num;
		return ProblemType.builder()
			.problemTypeCode(problemTypeCode)
			.name(name)
			.build();
	}

	public static QuestionType createQuestionType() {
		// 랜덤한 ProblemTypeDetail 객체 생성
		String num = "0"+RANDOM.nextInt(3);
		String questionTypeCode = "Q" + num;
		String name = "질문 유형 " + num;
		return QuestionType.builder()
			.questionTypeCode(questionTypeCode)
			.name(name)
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


}
