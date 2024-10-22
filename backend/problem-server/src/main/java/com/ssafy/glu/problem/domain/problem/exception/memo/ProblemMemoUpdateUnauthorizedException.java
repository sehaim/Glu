package com.ssafy.glu.problem.domain.problem.exception.memo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ProblemMemoUpdateUnauthorizedException extends ServiceException {
	private static final ErrorCode errorCode = ErrorCode.PROBLEM_MEMO_UPDATE_UNAUTHORIZED;

	public ProblemMemoUpdateUnauthorizedException() {
		super(errorCode);
	}

	public ProblemMemoUpdateUnauthorizedException(Exception exception) {
		super(errorCode, exception);
	}
}
