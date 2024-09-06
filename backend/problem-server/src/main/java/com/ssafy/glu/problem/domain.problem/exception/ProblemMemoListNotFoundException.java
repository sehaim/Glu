package com.ssafy.glu.problem.domain.problem.exception;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

public class ProblemMemoListNotFoundException extends ServiceException {
	public ProblemMemoListNotFoundException() {
		super(ErrorCode.PROBLEM_MEMO_LIST_NOT_FOUND);
	}

	public ProblemMemoListNotFoundException(Exception exception) {
		super(ErrorCode.PROBLEM_MEMO_LIST_NOT_FOUND, exception);
	}
}
