package com.ssafy.glu.problem.global.feign.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record UserResponse (
	Long id,
	String nickname,
	@JsonProperty("level")
	Integer stage,
	@JsonProperty("score")
	Integer exp,
	String imageUrl,
	Integer dayCount,
	List<UserProblemTypeResponse> problemTypeList
){}
