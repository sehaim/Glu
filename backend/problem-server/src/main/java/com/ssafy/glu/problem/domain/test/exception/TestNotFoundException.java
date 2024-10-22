package com.ssafy.glu.problem.domain.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TestNotFoundException extends ServiceException {
	private static final ErrorCode errorCode = ErrorCode.TEST_NOT_FOUND;

	public TestNotFoundException() {
		super(errorCode);
	}

	public TestNotFoundException(Exception exception) {
		super(errorCode, exception);
	}
}
