package com.ssafy.glu.problem.domain.problem.exception.user;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

public class NullUserAnswerException extends ServiceException {
	static ErrorCode errorCode = ErrorCode.NULL_USER_ANSWER;

	public NullUserAnswerException() {
		super(errorCode);
	}

	public NullUserAnswerException(Exception exception) {
		super(errorCode, exception);
	}
}
