package com.ssafy.glu.problem.domain.problem.exception.favorite;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FavoriteCancelFailedException extends ServiceException {
	private static final ErrorCode errorCode = ErrorCode.FAVORITE_CANCEL_FAILED;

	public FavoriteCancelFailedException() {
		super(errorCode);
	}

	public FavoriteCancelFailedException(Exception exception) {
		super(errorCode, exception);
	}
}
