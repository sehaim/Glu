package com.ssafy.glu.common.domain.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.common.global.error.ErrorCode;
import com.ssafy.glu.common.global.error.ServiceException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NullSearchCodeException extends ServiceException {
	public NullSearchCodeException() {
		super(ErrorCode.NULL_SEARCH_CODE);
	}

	public NullSearchCodeException(Exception exception) {
		super(ErrorCode.NULL_SEARCH_CODE, exception);
	}
}
