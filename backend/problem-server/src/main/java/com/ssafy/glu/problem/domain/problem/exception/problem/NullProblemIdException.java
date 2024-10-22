package com.ssafy.glu.problem.domain.problem.exception.problem;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NullProblemIdException extends ServiceException {
	private static final ErrorCode errorCode = ErrorCode.NULL_PROBLEM_ID;

	public NullProblemIdException() {
		super(errorCode);
	}

	public NullProblemIdException(Exception exception) {
		super(errorCode, exception);
	}
}
