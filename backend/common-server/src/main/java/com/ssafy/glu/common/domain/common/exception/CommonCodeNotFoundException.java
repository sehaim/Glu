package com.ssafy.glu.common.domain.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.common.global.error.ErrorCode;
import com.ssafy.glu.common.global.error.ServiceException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommonCodeNotFoundException extends ServiceException {
	public CommonCodeNotFoundException() {
		super(ErrorCode.COMMON_CODE_NOT_FOUND);
	}

	public CommonCodeNotFoundException(Exception exception) {
		super(ErrorCode.COMMON_CODE_NOT_FOUND, exception);
	}
}
