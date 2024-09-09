package com.ssafy.glu.problem.domain.problem.exception.memo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ProblemMemoCreateFailedException extends ServiceException {
	public ProblemMemoCreateFailedException() {
		super(ErrorCode.PROBLEM_MEMO_CREATION_FAILED);
	}

	public ProblemMemoCreateFailedException(Exception exception) {
		super(ErrorCode.PROBLEM_MEMO_CREATION_FAILED, exception);
	}
}
