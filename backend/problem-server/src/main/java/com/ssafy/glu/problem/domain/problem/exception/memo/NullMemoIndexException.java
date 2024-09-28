package com.ssafy.glu.problem.domain.problem.exception.memo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NullMemoIndexException extends ServiceException {
	private static final ErrorCode errorCode = ErrorCode.NULL_MEMO_INDEX;

	public NullMemoIndexException() {
		super(errorCode);
	}

	public NullMemoIndexException(Exception exception) {
		super(errorCode, exception);
	}
}
