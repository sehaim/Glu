package com.ssafy.glu.user.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 서버 오류
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다."),

    // 공통 오류
    BAD_REQUEST(400, "BAD_REQUEST", "잘못된 요청입니다."),
    UNAUTHORIZED(401, "UNAUTHORIZED", "인증이 필요합니다."),
    FORBIDDEN(403, "FORBIDDEN", "권한이 없습니다."),
    NOT_FOUND(404, "NOT_FOUND", "리소스를 찾을 수 없습니다."),
    CONFLICT(409, "CONFLICT", "리소스 충돌이 발생했습니다."),


    //유저 없음
    USER_NOT_FOUND(404, "USER_NOT_FOUND", "해당 유저를 찾을 수 없습니다."),
    DATE_INVALID(400, "DATE_INVALID", "날짜 정보가 올바르지 않습니다."),
    LOGIN_ID_DUPLICATE(400, "LOGIN_ID_DUPLICATE", "이미 존재하는 회원입니다."),
    PASSWORD_INVALID(403, "PASSWORD_INVALID", "패스워드를 확인해주세요");

    private final int httpStatus;
    private final String code;
    private final String message;
}