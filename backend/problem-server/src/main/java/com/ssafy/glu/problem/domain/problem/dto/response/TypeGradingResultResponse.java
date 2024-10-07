package com.ssafy.glu.problem.domain.problem.dto.response;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ssafy.glu.problem.domain.common.dto.CommonCodeResponse;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeCode;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemLog;

import lombok.Builder;

@Builder
public record TypeGradingResultResponse(
	Integer correctCount,
	CommonCodeResponse problemType,
	Integer acquiredScore,
	Integer totalScore
) {
	public static TypeGradingResultResponse of(Integer correctCount, CommonCodeResponse problemType,
		Integer acquiredScore, Integer totalScore) {
		return TypeGradingResultResponse.builder()
			.correctCount(correctCount)
			.problemType(problemType)
			.acquiredScore(acquiredScore)
			.totalScore(totalScore)
			.build();
	}

	public static List<TypeGradingResultResponse> createGradingResultByTypeList(
		List<UserProblemLog> userProblemLogList) {
		Map<ProblemTypeCode, List<UserProblemLog>> resultsByType = userProblemLogList.stream()
			.collect(Collectors.groupingBy(userProblemLog -> userProblemLog.getProblem().getProblemTypeCode()));

		return resultsByType.entrySet().stream()
			.sorted(Map.Entry.comparingByKey(Comparator.comparing(ProblemTypeCode::name)))
			.map(entry -> {
				ProblemTypeCode problemTypeCode = entry.getKey();
				List<UserProblemLog> typeGradeResults = entry.getValue();

				int correctCount = (int)typeGradeResults.stream().filter(UserProblemLog::isCorrect).count();

				return TypeGradingResultResponse.builder()
					.correctCount(correctCount)
					.problemType(CommonCodeResponse.of(problemTypeCode))
					.build();
			}).collect(Collectors.toList());
	}
}
