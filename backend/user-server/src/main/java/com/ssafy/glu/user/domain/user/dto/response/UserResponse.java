package com.ssafy.glu.user.domain.user.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

	private String id;
	private String nickname;
	private Integer level;
	private Integer score;
	private String imageUrl;
	private Integer dayCount;
	private List<ProblemTypeList> problemTypeList;
}
