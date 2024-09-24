package com.ssafy.glu.problem.global.feign.dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeCode;
import com.ssafy.glu.problem.domain.problem.dto.grading.GradeResult;

import lombok.Builder;

@Builder
public record ExpUpdateRequest(
	Map<ProblemTypeCode, Integer> userProblemTypeLevels,
	List<ProblemInfo> problemInfoList
){
	public static ExpUpdateRequest ofGradeResultList(List<UserProblemTypeResponse> userProblemTypeList, List<GradeResult> gradeResultList){
		return ExpUpdateRequest.builder()
			.userProblemTypeLevels(userProblemTypeListToMap(userProblemTypeList))
			.problemInfoList(gradeResultList.stream().filter((GradeResult::isCorrect)).map(ProblemInfo::from).toList())
			.build();
	}

	public static ExpUpdateRequest of(List<UserProblemTypeResponse> userProblemTypeList, List<Problem> problemList){
		return ExpUpdateRequest.builder()
			.userProblemTypeLevels(userProblemTypeListToMap(userProblemTypeList))
			.problemInfoList(problemList.stream().map(ProblemInfo::from).toList())
			.build();
	}

	private static Map<ProblemTypeCode, Integer> userProblemTypeListToMap(List<UserProblemTypeResponse> userProblemTypeList) {
		return userProblemTypeList.stream()
			.collect(Collectors.toMap(
				response -> ProblemTypeCode.valueOf(response.type().code()),  // key: ProblemTypeResponse의 code
				UserProblemTypeResponse::level       // value: UserProblemTypeResponse의 level
			));
	}
}
