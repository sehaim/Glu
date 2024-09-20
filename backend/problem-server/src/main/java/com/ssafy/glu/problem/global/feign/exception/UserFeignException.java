package com.ssafy.glu.problem.global.feign.exception;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

public class UserFeignException extends ServiceException {
	public UserFeignException() {
		super(ErrorCode.USER_FEIGN_ERROR);
	}

	public UserFeignException(Exception exception) {
		super(ErrorCode.USER_FEIGN_ERROR, exception);
	}
}
