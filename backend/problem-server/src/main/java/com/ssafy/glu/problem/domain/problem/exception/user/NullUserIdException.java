package com.ssafy.glu.problem.domain.problem.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NullUserIdException extends ServiceException {
	private static final ErrorCode errorCode = ErrorCode.NULL_USER_ID;

	public NullUserIdException() {
		super(errorCode);
	}

	public NullUserIdException(Exception exception) {
		super(errorCode, exception);
	}
}
