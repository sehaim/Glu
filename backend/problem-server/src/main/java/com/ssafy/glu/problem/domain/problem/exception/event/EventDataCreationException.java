package com.ssafy.glu.problem.domain.problem.exception.event;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class EventDataCreationException extends ServiceException {
	private static final ErrorCode errorCode = ErrorCode.EVENT_DATE_CREATION_FAILED;

	public EventDataCreationException() {
		super(errorCode);
	}

	public EventDataCreationException(Exception exception) {
		super(errorCode, exception);
	}
}
