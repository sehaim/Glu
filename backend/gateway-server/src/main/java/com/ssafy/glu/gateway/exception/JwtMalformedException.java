package com.ssafy.glu.gateway.exception;

import com.ssafy.glu.gateway.error.ErrorCode;
import com.ssafy.glu.gateway.error.ServiceException;
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