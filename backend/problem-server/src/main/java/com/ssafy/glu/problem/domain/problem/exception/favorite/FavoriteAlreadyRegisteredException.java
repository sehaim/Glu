package com.ssafy.glu.problem.domain.problem.exception.favorite;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.CONFLICT)
public class FavoriteAlreadyRegisteredException extends ServiceException {
	private static final ErrorCode errorCode = ErrorCode.FAVORITE_ALREADY_REGISTERED;

	public FavoriteAlreadyRegisteredException() {
		super(errorCode);
	}

	public FavoriteAlreadyRegisteredException(Exception exception) {
		super(errorCode, exception);
	}
}
