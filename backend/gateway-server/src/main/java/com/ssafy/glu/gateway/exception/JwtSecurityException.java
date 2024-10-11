package com.ssafy.glu.gateway.exception;

import com.ssafy.glu.gateway.error.ErrorCode;
import com.ssafy.glu.gateway.error.ServiceException;
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
