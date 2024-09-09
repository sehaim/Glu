package com.ssafy.glu.problem.domain.problem.exception.memo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ProblemMemoDeletedUnauthorizedException extends ServiceException {
	public ProblemMemoDeletedUnauthorizedException() {
		super(ErrorCode.PROBLEM_MEMO_DELETE_UNAUTHORIZED);
	}

	public ProblemMemoDeletedUnauthorizedException(Exception exception) {
		super(ErrorCode.PROBLEM_MEMO_DELETE_UNAUTHORIZED, exception);
	}
}
