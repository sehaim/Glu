package com.ssafy.glu.auth.domain.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.auth.global.error.ErrorCode;
import com.ssafy.glu.auth.global.error.ServiceException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoginInValidException extends ServiceException {

	public LoginInValidException() {
		super(ErrorCode.LOGIN_INVALID);
	}

	public LoginInValidException(Exception exception) {
		super(ErrorCode.LOGIN_INVALID, exception);
	}
}