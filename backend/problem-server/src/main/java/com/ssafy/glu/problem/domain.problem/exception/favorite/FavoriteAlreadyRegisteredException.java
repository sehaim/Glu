package com.ssafy.glu.problem.domain.problem.exception.favorite;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.CONFLICT)
public class FavoriteAlreadyRegisteredException extends ServiceException {
	public FavoriteAlreadyRegisteredException() {
		super(ErrorCode.FAVORITE_ALREADY_REGISTERED);
	}

	public FavoriteAlreadyRegisteredException(Exception exception) {
		super(ErrorCode.FAVORITE_ALREADY_REGISTERED, exception);
	}
}
