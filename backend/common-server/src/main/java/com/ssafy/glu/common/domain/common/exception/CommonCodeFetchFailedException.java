package com.ssafy.glu.common.domain.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.common.global.error.ErrorCode;
import com.ssafy.glu.common.global.error.ServiceException;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CommonCodeFetchFailedException extends ServiceException {
	public CommonCodeFetchFailedException() {
		super(ErrorCode.COMMON_CODE_FETCH_FAILED);
	}

	public CommonCodeFetchFailedException(Exception exception) {
		super(ErrorCode.COMMON_CODE_FETCH_FAILED, exception);
	}
}
