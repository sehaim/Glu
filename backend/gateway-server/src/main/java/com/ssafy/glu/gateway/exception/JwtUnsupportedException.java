package com.ssafy.glu.gateway.exception;

import com.ssafy.glu.gateway.error.ErrorCode;
import com.ssafy.glu.gateway.error.ServiceException;
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