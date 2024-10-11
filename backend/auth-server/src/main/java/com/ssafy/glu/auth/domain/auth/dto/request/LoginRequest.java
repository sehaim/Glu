package com.ssafy.glu.auth.domain.auth.dto.request;

public record LoginRequest(
	String id,
	String password
) {}
