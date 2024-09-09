package com.ssafy.glu.user.domain.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.user.global.error.ErrorCode;
import com.ssafy.glu.user.global.error.ServiceException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DateInValidException extends ServiceException {

	public DateInValidException() {
		super(ErrorCode.DATE_INVALID);
	}

	public DateInValidException(Exception exception) {
		super(ErrorCode.DATE_INVALID, exception);
	}
}