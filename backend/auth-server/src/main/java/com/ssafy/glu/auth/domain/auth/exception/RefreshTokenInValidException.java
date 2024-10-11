package com.ssafy.glu.auth.domain.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.auth.global.error.ErrorCode;
import com.ssafy.glu.auth.global.error.ServiceException;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class RefreshTokenInValidException extends ServiceException {

	public RefreshTokenInValidException() {
		super(ErrorCode.REFRESH_TOKEN_INVALID);
	}

	public RefreshTokenInValidException(Exception exception) {
		super(ErrorCode.REFRESH_TOKEN_INVALID, exception);
	}
}