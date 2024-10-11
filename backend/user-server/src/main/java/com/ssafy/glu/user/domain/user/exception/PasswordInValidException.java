package com.ssafy.glu.user.domain.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.user.global.error.ErrorCode;
import com.ssafy.glu.user.global.error.ServiceException;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PasswordInValidException extends ServiceException {

	public PasswordInValidException() {
		super(ErrorCode.PASSWORD_INVALID);
	}

	public PasswordInValidException(Exception exception) {
		super(ErrorCode.PASSWORD_INVALID, exception);
	}
}