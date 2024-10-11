package com.ssafy.glu.auth.domain.auth.exception;

import com.ssafy.glu.auth.global.error.ErrorCode;
import com.ssafy.glu.auth.global.error.ServiceException;

import lombok.Getter;

@Getter
public class JwtSecurityException extends ServiceException {
    public JwtSecurityException() {
        super(ErrorCode.INVALID_TOKEN);
    }

    public JwtSecurityException(Throwable cause) {
        super(ErrorCode.INVALID_TOKEN, cause);
    }
}
