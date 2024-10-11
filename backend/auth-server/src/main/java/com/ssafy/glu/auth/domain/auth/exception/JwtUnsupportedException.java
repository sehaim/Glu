package com.ssafy.glu.auth.domain.auth.exception;

import com.ssafy.glu.auth.global.error.ErrorCode;
import com.ssafy.glu.auth.global.error.ServiceException;

import lombok.Getter;

@Getter
public class JwtUnsupportedException extends ServiceException {
    public JwtUnsupportedException() {
        super(ErrorCode.UNSUPPORTED_TOKEN);
    }

    public JwtUnsupportedException(Throwable cause) {
        super(ErrorCode.UNSUPPORTED_TOKEN, cause);
    }
}
