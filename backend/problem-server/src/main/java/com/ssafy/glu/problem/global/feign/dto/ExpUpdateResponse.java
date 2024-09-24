package com.ssafy.glu.problem.global.feign.dto;

public record ExpUpdateResponse(
	Boolean isStageUp,
	String stageUpUrl
){
}
