package com.ssafy.glu.problem.domain.problem.exception.problem;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ProblemTypeCodeMismatchException extends ServiceException {
	private static final ErrorCode errorCode = ErrorCode.PROBLEM_TYPE_CODE_MISMATCH;

	public ProblemTypeCodeMismatchException() {
		super(errorCode);
	}

	public ProblemTypeCodeMismatchException(Exception exception) {
		super(errorCode, exception);
	}
}
