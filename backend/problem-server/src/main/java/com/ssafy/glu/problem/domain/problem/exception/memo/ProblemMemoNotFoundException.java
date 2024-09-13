package com.ssafy.glu.problem.domain.problem.exception.memo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProblemMemoNotFoundException extends ServiceException {
	public ProblemMemoNotFoundException() {
		super(ErrorCode.PROBLEM_MEMO_NOT_FOUND);
	}

	public ProblemMemoNotFoundException(Exception exception) {
		super(ErrorCode.PROBLEM_MEMO_NOT_FOUND, exception);
	}
}
