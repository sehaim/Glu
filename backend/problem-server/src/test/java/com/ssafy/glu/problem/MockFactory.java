package com.ssafy.glu.problem;

import java.util.Random;
import java.util.UUID;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.ProblemLevel;

public class MockFactory {

	private static final Random RANDOM = new Random();

	public static Problem createProblem() {
		// 랜덤한 문자열 생성
		String randomTitle = "Title " + UUID.randomUUID().toString().substring(0, 8);
		String randomContent = "Content " + UUID.randomUUID().toString().substring(0, 8);
		String randomSolution = "Solution " + UUID.randomUUID().toString().substring(0, 8);

		return Problem.builder()
			.title(randomTitle)
			.content(randomContent)
			.solution(randomSolution)
			.level(createProblemLevel())
			.build();
	}

	public static ProblemLevel createProblemLevel() {
		// 랜덤한 ProblemLevel 객체 생성
		String randomLevel = "0"+RANDOM.nextInt(3);
		String randomCode = "PL" + randomLevel;
		String randomName = "LV " + randomLevel;
		return ProblemLevel.builder()
			.problemLevelCode(randomCode)
			.name(randomName)
			.build();
	}

}
