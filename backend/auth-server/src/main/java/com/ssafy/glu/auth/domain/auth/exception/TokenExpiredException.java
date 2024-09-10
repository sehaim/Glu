package com.ssafy.glu.auth.domain.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.auth.global.error.ErrorCode;
import com.ssafy.glu.auth.global.error.ServiceException;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenExpiredException extends ServiceException {

	public TokenExpiredException() {
		super(ErrorCode.TOKEN_EXPIRED);
	}

	public TokenExpiredException(Exception exception) {
		super(ErrorCode.TOKEN_EXPIRED, exception);
	}
}