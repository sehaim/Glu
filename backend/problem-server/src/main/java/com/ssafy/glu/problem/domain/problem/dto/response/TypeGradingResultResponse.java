package com.ssafy.glu.problem.domain.problem.dto.response;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ssafy.glu.problem.domain.common.dto.CommonCodeResponse;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeCode;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemLog;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "유형 채점 결과 응답 DTO")
public record TypeGradingResultResponse(
	@Schema(description = "정답 수", example = "3")
	Integer correctCount,

	@Schema(description = "문제 유형")
	CommonCodeResponse problemType,

	@Schema(description = "획득 점수", example = "10")
	Integer acquiredScore,

	@Schema(description = "총 점수", example = "100")
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
