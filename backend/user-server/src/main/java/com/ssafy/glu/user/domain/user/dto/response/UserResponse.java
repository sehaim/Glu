package com.ssafy.glu.user.domain.user.dto.response;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;

@Builder
public record UserResponse (
	String id,
	String nickname,
	Integer stage,
	Integer exp,
	String imageUrl,
	Integer dayCount,
	LocalDate birth,
	LocalDate createDate,
	Integer attendanceRate,
	List<UserProblemTypeResponse> problemTypeList
){}
