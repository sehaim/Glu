package com.ssafy.glu.problem.global.feign.exception;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

public class UserFeignException extends ServiceException {
	static ErrorCode errorCode = ErrorCode.USER_FEIGN_ERROR;

	public UserFeignException() {
		super(errorCode);
	}

	public UserFeignException(Exception exception) {
		super(errorCode, exception);
	}
}
