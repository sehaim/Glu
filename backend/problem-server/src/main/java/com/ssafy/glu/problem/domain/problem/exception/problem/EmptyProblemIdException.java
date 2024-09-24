package com.ssafy.glu.problem.domain.problem.exception.problem;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

public class EmptyProblemIdException extends ServiceException {
	static ErrorCode errorCode = ErrorCode.NULL_PROBLEM_ID;

	public EmptyProblemIdException() {
		super(errorCode);
	}

	public EmptyProblemIdException(Exception exception) {
		super(errorCode, exception);
	}
}
