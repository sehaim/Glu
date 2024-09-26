package com.ssafy.glu.problem.domain.test.dto.response;

import com.ssafy.glu.problem.domain.common.dto.CommonCodeResponse;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeCode;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemLog;
import com.ssafy.glu.problem.domain.problem.dto.grading.GradeResult;
import com.ssafy.glu.problem.domain.problem.dto.response.ProblemGradingResultResponse;
import com.ssafy.glu.problem.domain.problem.dto.response.TypeGradingResultResponse;
import com.ssafy.glu.problem.domain.test.domain.Test;
import lombok.Builder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
public record TestGradingDetailResponse (
    // Test
    String testId,
    Integer totalCorrectCount,
    Integer totalSolvedTime,

    List<TypeGradingResultResponse> gradingResultByTypeList,
    List<ProblemGradingResultResponse> gradingResultByProblemList
){
    public static TestGradingDetailResponse of(Test test, List<UserProblemLog> userProblemLogList){
        if(test == null) return null;
        return TestGradingDetailResponse.builder()
                .testId(test.getTestId())
                .totalCorrectCount(test.getCorrectCount())
                .totalSolvedTime(test.getTotalSolveTime())
                .gradingResultByTypeList(createGradingResultByTypeList(userProblemLogList))
                .gradingResultByProblemList(createGradingResultByProblemList(userProblemLogList))
                .build();
    }

    public static List<TypeGradingResultResponse> createGradingResultByTypeList(List<UserProblemLog> userProblemLogList) {
        Map<ProblemTypeCode, List<UserProblemLog>> resultsByType = userProblemLogList.stream()
                .collect(Collectors.groupingBy(userProblemLog -> userProblemLog.getProblem().getProblemTypeCode()));

        return resultsByType.entrySet().stream()
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

    public static List<ProblemGradingResultResponse> createGradingResultByProblemList(List<UserProblemLog> userProblemLogList) {
        return userProblemLogList.stream().map(ProblemGradingResultResponse::of).toList();
    }
}
