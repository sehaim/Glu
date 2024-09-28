package com.ssafy.glu.problem.domain.problem.exception.memo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProblemMemoListNotFoundException extends ServiceException {
	private static final ErrorCode errorCode = ErrorCode.PROBLEM_MEMO_LIST_NOT_FOUND;

	public ProblemMemoListNotFoundException() {
		super(errorCode);
	}

	public ProblemMemoListNotFoundException(Exception exception) {
		super(errorCode, exception);
	}
}
