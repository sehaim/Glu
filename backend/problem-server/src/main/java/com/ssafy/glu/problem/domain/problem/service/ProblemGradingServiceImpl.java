package com.ssafy.glu.problem.domain.problem.service;

import org.springframework.stereotype.Service;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.dto.grading.GradeResult;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSolveRequest;
import com.ssafy.glu.problem.domain.problem.exception.problem.ProblemTypeCodeMismatchException;
import com.ssafy.glu.problem.global.feign.dto.UserProblemTypeResponse;
import com.ssafy.glu.problem.global.feign.dto.UserResponse;
import com.ssafy.glu.problem.global.util.ScoreUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProblemGradingServiceImpl implements ProblemGradingService {
	@Override
	public GradeResult gradeProblem(UserResponse user, Problem problem, ProblemSolveRequest request) {
		// 문제 유형별 채점
		log.info("[문제 유형별 채점]");
		boolean isCorrect = problem.grade(request.userAnswer());

		// 문제 유형에 맞는 유저 점수 조회
		log.info("[문제 유형에 맞는 유저 점수 조회]");
		UserProblemTypeResponse userProblemType = getUserProblemType(user, problem);

		// 유저 점수 업데이트
		log.info("[유저 점수 업데이트]");
		int userTotalScore = ScoreUtil.calculateTotalScore(userProblemType);
		int acquiredScore = ScoreUtil.calculateAcquiredScore(userTotalScore, problem.score(), isCorrect);

		return GradeResult.builder()
			.isCorrect(isCorrect)
			.userLevel(userProblemType.level())
			.userScore(userProblemType.score())
			.acquiredScore(acquiredScore)
			.problemTypeCode(problem.getProblemTypeCode())
			.problemLevel(problem.getProblemLevelCode().getLevel())
			.build();
	}

	private UserProblemTypeResponse getUserProblemType(UserResponse user, Problem problem) {
		return user.problemTypeList()
			.stream()
			.filter(problemType -> problem.getProblemTypeCode().name().equals(problemType.type().code()))
			.findFirst()
			.orElseThrow(ProblemTypeCodeMismatchException::new);
	}
}