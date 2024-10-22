package com.ssafy.glu.problem.global.feign.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record UserResponse(
	String id,
	String nickname,
	Integer stage,
	Integer exp,
	String imageUrl,
	Integer dayCount,
	List<UserProblemTypeResponse> problemTypeList
) {
}
