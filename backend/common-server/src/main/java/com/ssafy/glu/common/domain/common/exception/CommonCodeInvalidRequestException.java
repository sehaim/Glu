package com.ssafy.glu.common.domain.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.common.global.error.ErrorCode;
import com.ssafy.glu.common.global.error.ServiceException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CommonCodeInvalidRequestException extends ServiceException {
	public CommonCodeInvalidRequestException() {
		super(ErrorCode.COMMON_CODE_INVALID_REQUEST);
	}

	public CommonCodeInvalidRequestException(Exception exception) {
		super(ErrorCode.COMMON_CODE_INVALID_REQUEST, exception);
	}
}
