package com.ssafy.glu.user.domain.user.dto.response;

import com.ssafy.glu.user.domain.user.domain.UserProblemType;

public record UserProblemTypeResponse(Integer level, Integer score, ProblemTypeDTO type){
	public static UserProblemTypeResponse of(UserProblemType userProblemType){
		return new UserProblemTypeResponse(
			userProblemType.getLevel(),
			userProblemType.getScore(),
			new ProblemTypeDTO(userProblemType.getProblemTypeCode())
			);
	}
}
