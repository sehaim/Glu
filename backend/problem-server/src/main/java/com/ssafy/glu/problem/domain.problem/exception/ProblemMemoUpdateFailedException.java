package com.ssafy.glu.problem.domain.problem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ProblemMemoUpdateFailedException extends ServiceException {
	public ProblemMemoUpdateFailedException() {
		super(ErrorCode.PROBLEM_MEMO_UPDATE_FAILED);
	}

	public ProblemMemoUpdateFailedException(Exception exception) {
		super(ErrorCode.PROBLEM_MEMO_UPDATE_FAILED, exception);
	}
}
