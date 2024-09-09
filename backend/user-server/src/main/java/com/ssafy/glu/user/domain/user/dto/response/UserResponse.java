package com.ssafy.glu.user.domain.user.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record UserResponse (
	Long id,
	String nickname,
	Integer level,
	Integer score,
	String imageUrl,
	Integer dayCount,
	List<ProblemTypeList> problemTypeList
){}
