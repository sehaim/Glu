package com.ssafy.glu.auth.domain.auth.exception;

import com.ssafy.glu.auth.global.error.ErrorCode;
import com.ssafy.glu.auth.global.error.ServiceException;

import lombok.Getter;

@Getter
public class JwtExpiredException extends ServiceException {
    public JwtExpiredException() {
        super(ErrorCode.REFRESH_TOKEN_EXPIRED);
    }

    public JwtExpiredException(Throwable cause) {
        super(ErrorCode.REFRESH_TOKEN_EXPIRED, cause);
    }
}
