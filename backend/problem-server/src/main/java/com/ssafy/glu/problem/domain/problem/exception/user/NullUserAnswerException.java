package com.ssafy.glu.problem.domain.problem.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NullUserAnswerException extends ServiceException {
	private static final ErrorCode errorCode = ErrorCode.NULL_USER_ANSWER;

	public NullUserAnswerException() {
		super(errorCode);
	}

	public NullUserAnswerException(Exception exception) {
		super(errorCode, exception);
	}
}
