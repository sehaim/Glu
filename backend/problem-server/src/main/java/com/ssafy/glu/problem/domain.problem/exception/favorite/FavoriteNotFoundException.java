package com.ssafy.glu.problem.domain.problem.exception.favorite;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FavoriteNotFoundException extends ServiceException {
	public FavoriteNotFoundException() {
		super(ErrorCode.FAVORITE_NOT_FOUND);
	}

	public FavoriteNotFoundException(Exception exception) {
		super(ErrorCode.FAVORITE_NOT_FOUND, exception);
	}
}
