package com.ssafy.glu.problem.global.feign.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserFeignException extends ServiceException {
	static ErrorCode errorCode = ErrorCode.USER_FEIGN_ERROR;

	public UserFeignException() {
		super(errorCode);
	}

	public UserFeignException(Exception exception) {
		super(errorCode, exception);
	}
}
