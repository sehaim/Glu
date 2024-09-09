package com.ssafy.glu.user.domain.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.user.global.error.ErrorCode;
import com.ssafy.glu.user.global.error.ServiceException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends ServiceException {

	public UserNotFoundException() {
		super(ErrorCode.USER_NOT_FOUND);
	}

	public UserNotFoundException(Exception exception) {
		super(ErrorCode.USER_NOT_FOUND, exception);
	}
}