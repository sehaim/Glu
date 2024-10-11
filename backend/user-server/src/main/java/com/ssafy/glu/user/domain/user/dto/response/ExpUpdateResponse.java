package com.ssafy.glu.user.domain.user.dto.response;

public record ExpUpdateResponse (
	Boolean isStageUp,
	String stageUpUrl
) {}
