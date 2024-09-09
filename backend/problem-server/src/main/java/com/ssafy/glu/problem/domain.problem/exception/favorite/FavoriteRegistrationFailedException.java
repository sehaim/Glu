package com.ssafy.glu.problem.domain.problem.exception.favorite;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FavoriteRegistrationFailedException extends ServiceException {
	public FavoriteRegistrationFailedException() {
		super(ErrorCode.FAVORITE_REGISTRATION_FAILED);
	}

	public FavoriteRegistrationFailedException(Exception exception) {
		super(ErrorCode.FAVORITE_REGISTRATION_FAILED, exception);
	}
}
