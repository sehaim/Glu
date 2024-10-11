package com.ssafy.glu.common.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	// 서버 오류
	INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다."),

	// 공통 코드 조회 오류
	COMMON_CODE_NOT_FOUND(404, "COMMON_CODE_NOT_FOUND", "공통 코드를 찾을 수 없습니다."),
	COMMON_CODE_FETCH_FAILED(500, "COMMON_CODE_FETCH_FAILED", "공통 코드 조회에 실패했습니다."),
	NULL_SEARCH_CODE(400, "NULL_SEARCH_CODE", "검색할 코드가 null입니다."),
	COMMON_CODE_INVALID_REQUEST(400, "COMMON_CODE_INVALID_REQUEST", "잘못된 공통 코드 요청입니다.");

	private final int httpStatus;
	private final String code;
	private final String message;
}