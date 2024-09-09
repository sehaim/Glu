package com.ssafy.glu.problem.domain.problem.exception.status;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserProblemStatusNotFoundException extends ServiceException {
	public UserProblemStatusNotFoundException() {
		super(ErrorCode.USER_PROBLEM_STATUS_NOT_FOUND);
	}

	public UserProblemStatusNotFoundException(Exception exception) {
		super(ErrorCode.USER_PROBLEM_STATUS_NOT_FOUND, exception);
	}
}
