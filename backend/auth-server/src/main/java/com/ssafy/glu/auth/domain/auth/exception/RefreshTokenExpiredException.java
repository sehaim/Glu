package com.ssafy.glu.auth.domain.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.auth.global.error.ErrorCode;
import com.ssafy.glu.auth.global.error.ServiceException;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class RefreshTokenExpiredException extends ServiceException {

	public RefreshTokenExpiredException() {
		super(ErrorCode.REFRESH_TOKEN_EXPIRED);
	}

	public RefreshTokenExpiredException(Exception exception) {
		super(ErrorCode.REFRESH_TOKEN_EXPIRED, exception);
	}
}