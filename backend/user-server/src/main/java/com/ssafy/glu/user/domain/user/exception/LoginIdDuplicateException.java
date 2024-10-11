package com.ssafy.glu.user.domain.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.user.global.error.ErrorCode;
import com.ssafy.glu.user.global.error.ServiceException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoginIdDuplicateException extends ServiceException {

	public LoginIdDuplicateException() {
		super(ErrorCode.LOGIN_ID_DUPLICATE);
	}

	public LoginIdDuplicateException(Exception exception) {
		super(ErrorCode.LOGIN_ID_DUPLICATE, exception);
	}
}