package com.ssafy.glu.problem.domain.problem.exception.problem;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

public class NullProblemIdException extends ServiceException {
	public NullProblemIdException() {
		super(ErrorCode.NULL_PROBLEM_ID);
	}

	public NullProblemIdException(Exception exception) {
		super(ErrorCode.NULL_PROBLEM_ID, exception);
	}
}
