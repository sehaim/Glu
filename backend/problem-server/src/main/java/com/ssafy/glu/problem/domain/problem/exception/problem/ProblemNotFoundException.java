package com.ssafy.glu.problem.domain.problem.exception.problem;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProblemNotFoundException extends ServiceException {
	private static final ErrorCode errorCode = ErrorCode.PROBLEM_NOT_FOUND;

	public ProblemNotFoundException() {
		super(errorCode);
	}

	public ProblemNotFoundException(Exception exception) {
		super(errorCode, exception);
	}
}
