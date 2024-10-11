package com.ssafy.glu.auth.domain.auth.exception;

import com.ssafy.glu.auth.global.error.ErrorCode;
import com.ssafy.glu.auth.global.error.ServiceException;

import lombok.Getter;

@Getter
public class JwtMalformedException extends ServiceException {
    public JwtMalformedException() {
        super(ErrorCode.BAD_REQUEST);
    }

    public JwtMalformedException(Throwable cause) {
        super(ErrorCode.BAD_REQUEST, cause);
    }
}
