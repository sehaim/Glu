package com.ssafy.glu.problem.domain.problem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FavoriteRegistrationFailedException extends ServiceException {
	public FavoriteRegistrationFailedException() {
		super(ErrorCode.FAVORITE_REGISTRATION_FAILED);
	}
}
