package com.ssafy.glu.problem.domain.problem.exception.favorite;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FavoriteNotRegisteredException extends ServiceException {
	public FavoriteNotRegisteredException() {
		super(ErrorCode.FAVORITE_NOT_REGISTERED);
	}

	public FavoriteNotRegisteredException(Exception exception) {
		super(ErrorCode.FAVORITE_NOT_REGISTERED, exception);
	}
}
