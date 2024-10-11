package com.ssafy.glu.gateway.exception;

import com.ssafy.glu.gateway.error.ErrorCode;
import com.ssafy.glu.gateway.error.ServiceException;
import lombok.Getter;

@Getter
public class JwtExpiredException extends ServiceException {
    public JwtExpiredException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }

    public JwtExpiredException(Throwable cause) {
        super(ErrorCode.EXPIRED_TOKEN, cause);
    }
}