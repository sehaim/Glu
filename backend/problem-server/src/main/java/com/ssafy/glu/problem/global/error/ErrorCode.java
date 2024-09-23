package com.ssafy.glu.problem.global.error;

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

	// 필수 값이 누락된
	NULL_USER_ANSWER(400, "NULL_USER_ANSWER", "userAnswer가 null입니다."),
	NULL_USER_ID(400, "NULL_USER_ID", "userId가 null입니다."),
	NULL_PROBLEM_ID(400, "NULL_PROBLEM_ID", "problemId가 null입니다."),
	NULL_MEMO_INDEX(400, "NULL_MEMO_INDEX", "memoIndex가 null입니다."),

	EMPTY_PROBLEM_ID(400, "EMPTY_PROBLEM_ID", "problemId가 비어있습니다."),

	// 문제 풀이 이력 조회
	SOLVE_HISTORY_NOT_FOUND(404, "SOLVE_HISTORY_NOT_FOUND", "문제 풀이 이력을 찾을 수 없습니다."),

	// 문제 메모 추가
	PROBLEM_MEMO_CREATION_FAILED(500, "PROBLEM_MEMO_CREATION_FAILED", "문제 메모 추가에 실패했습니다."),
	PROBLEM_MEMO_INVALID_REQUEST(400, "PROBLEM_MEMO_INVALID_REQUEST", "문제 메모 요청이 잘못되었습니다."),

	// 문제 메모 수정
	PROBLEM_MEMO_NOT_FOUND(404, "PROBLEM_MEMO_NOT_FOUND", "해당 메모를 찾을 수 없습니다."),
	PROBLEM_MEMO_UPDATE_FAILED(500, "PROBLEM_MEMO_UPDATE_FAILED", "문제 메모 수정에 실패했습니다."),
	PROBLEM_MEMO_UPDATE_UNAUTHORIZED(403, "PROBLEM_MEMO_UPDATE_UNAUTHORIZED", "해당 메모를 수정할 권한이 없습니다."),

	// 문제 메모 삭제
	PROBLEM_MEMO_DELETE_FAILED(500, "PROBLEM_MEMO_DELETE_FAILED", "문제 메모 삭제에 실패했습니다."),
	PROBLEM_MEMO_DELETE_UNAUTHORIZED(403, "PROBLEM_MEMO_DELETE_UNAUTHORIZED", "해당 메모를 삭제할 권한이 없습니다."),

	// 문제 메모 리스트 조회
	PROBLEM_MEMO_LIST_NOT_FOUND(404, "PROBLEM_MEMO_LIST_NOT_FOUND", "해당 문제에 대한 메모를 찾을 수 없습니다."),

	// 찜한 문제 조회
	FAVORITE_PROBLEMS_NOT_FOUND(404, "FAVORITE_PROBLEMS_NOT_FOUND", "찜한 문제를 찾을 수 없습니다."),

	// 문제 찜 등록
	FAVORITE_REGISTRATION_FAILED(500, "FAVORITE_REGISTRATION_FAILED", "문제 찜 등록에 실패했습니다."),
	FAVORITE_ALREADY_REGISTERED(409, "FAVORITE_ALREADY_REGISTERED", "이미 찜한 문제입니다."),

	// 문제 찜 취소
	FAVORITE_CANCEL_FAILED(500, "FAVORITE_CANCEL_FAILED", "문제 찜 취소에 실패했습니다."),
	FAVORITE_NOT_REGISTERED(400, "FAVORITE_NOT_REGISTERED", "찜한 문제가 아닙니다."),

	// 문제 리스트 채점
	GRADING_LIST_FAILED(500, "GRADING_LIST_FAILED", "문제 리스트 채점에 실패했습니다."),
	INVALID_PROBLEM_LIST(400, "INVALID_PROBLEM_LIST", "잘못된 문제 리스트입니다."),

	// 단일 문제 채점
	GRADING_FAILED(500, "GRADING_FAILED", "문제 채점에 실패했습니다."),
	PROBLEM_NOT_FOUND(404, "PROBLEM_NOT_FOUND", "해당 문제를 찾을 수 없습니다."),

	// 이전 테스트 결과 조회
	PREVIOUS_TEST_RESULT_NOT_FOUND(404, "PREVIOUS_TEST_RESULT_NOT_FOUND", "이전 테스트 결과를 찾을 수 없습니다."),

	// 테스트 결과 리스트 조회
	TEST_RESULT_LIST_NOT_FOUND(404, "TEST_RESULT_LIST_NOT_FOUND", "테스트 결과 리스트를 찾을 수 없습니다."),

	USER_FEIGN_ERROR(500, "USER_FEIGN_ERROR", "user server API요청이 실패하였습니다."),
	PROBLEM_TYPE_CODE_MISMATCH(400, "PROBLEM_TYPE_CODE_MISMATCH", "일치하는 문제 유형이 존재하지 않습니다."),

	USER_PROBLEM_STATUS_NOT_FOUND(404, "USER_PROBLEM_STATUS_NOT_FOUND", "문제 풀이 상태를 찾을 수 없습니다.");

	private final int httpStatus;
	private final String code;
	private final String message;
}