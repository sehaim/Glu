package com.ssafy.glu.problem.domain.problem.exception.user;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

public class NullUserIdException extends ServiceException {
	public NullUserIdException() {
		super(ErrorCode.NULL_USER_ID);
	}

	public NullUserIdException(Exception exception) {
		super(ErrorCode.NULL_USER_ID, exception);
	}
}
