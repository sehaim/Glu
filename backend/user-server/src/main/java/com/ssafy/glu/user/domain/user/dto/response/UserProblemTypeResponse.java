package com.ssafy.glu.user.domain.user.dto.response;

import com.ssafy.glu.user.domain.user.domain.ProblemType;
import com.ssafy.glu.user.domain.user.domain.UserProblemType;

public record UserProblemTypeResponse(Integer level, Integer score, ProblemType type){
	public static UserProblemTypeResponse of(UserProblemType userProblemType){
		return new UserProblemTypeResponse(
			userProblemType.getLevel(),
			userProblemType.getScore(),
			userProblemType.getProblemTypeCode()
			);
	}
}
