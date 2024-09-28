package com.ssafy.glu.problem.domain.problem.exception.status;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserProblemStatusNotFoundException extends ServiceException {
	private static final ErrorCode errorCode = ErrorCode.USER_PROBLEM_STATUS_NOT_FOUND;

	public UserProblemStatusNotFoundException() {
		super(errorCode);
	}

	public UserProblemStatusNotFoundException(Exception exception) {
		super(errorCode, exception);
	}
}
