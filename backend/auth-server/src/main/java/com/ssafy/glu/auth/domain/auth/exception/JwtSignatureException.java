package com.ssafy.glu.auth.domain.auth.exception;

import com.ssafy.glu.auth.global.error.ErrorCode;
import com.ssafy.glu.auth.global.error.ServiceException;

import lombok.Getter;

@Getter
public class JwtSignatureException extends ServiceException {
    public JwtSignatureException() {
        super(ErrorCode.INVALID_TOKEN);
    }

    public JwtSignatureException(Throwable cause) {
        super(ErrorCode.INVALID_TOKEN, cause);
    }
}
