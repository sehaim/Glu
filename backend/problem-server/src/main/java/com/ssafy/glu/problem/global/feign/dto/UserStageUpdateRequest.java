package com.ssafy.glu.problem.global.feign.dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeCode;

import lombok.Builder;

@Builder
public record UserStageUpdateRequest (
	Map<ProblemTypeCode, Integer> userProblemTypeLevels,
	List<ProblemInfo> problemInfoList
){
	public static UserStageUpdateRequest of(List<UserProblemTypeResponse> userProblemTypeList, List<Problem> problemList){
		return UserStageUpdateRequest.builder()
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
