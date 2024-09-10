package com.ssafy.glu.gateway.exception;

import com.ssafy.glu.gateway.error.ErrorCode;
import com.ssafy.glu.gateway.error.ServiceException;
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
