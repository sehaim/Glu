package com.ssafy.glu.auth.domain.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.auth.global.error.ErrorCode;
import com.ssafy.glu.auth.global.error.ServiceException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends ServiceException {

	public UserNotFoundException() {
		super(ErrorCode.USER_NOT_FOUND);
	}

	public UserNotFoundException(Exception exception) {
		super(ErrorCode.USER_NOT_FOUND, exception);
	}
}