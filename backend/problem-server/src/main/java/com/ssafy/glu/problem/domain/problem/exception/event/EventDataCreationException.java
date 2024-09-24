package com.ssafy.glu.problem.domain.problem.exception.event;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

public class EventDataCreationException extends ServiceException {
	static ErrorCode errorCode = ErrorCode.EVENT_DATE_CREATION_FAILED;

	public EventDataCreationException() {
		super(errorCode);
	}

	public EventDataCreationException(Exception exception) {
		super(errorCode, exception);
	}
}
