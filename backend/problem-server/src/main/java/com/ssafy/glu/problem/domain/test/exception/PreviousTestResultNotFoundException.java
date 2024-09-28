package com.ssafy.glu.problem.domain.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PreviousTestResultNotFoundException extends ServiceException {
	private static final ErrorCode errorCode = ErrorCode.PREVIOUS_TEST_RESULT_NOT_FOUND;

	public PreviousTestResultNotFoundException() {
		super(errorCode);
	}

	public PreviousTestResultNotFoundException(Exception exception) {
		super(errorCode, exception);
	}
}
