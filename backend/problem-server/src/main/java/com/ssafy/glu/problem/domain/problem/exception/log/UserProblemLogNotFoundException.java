package com.ssafy.glu.problem.domain.problem.exception.log;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

public class UserProblemLogNotFoundException extends ServiceException {
    private static final ErrorCode errorCode = ErrorCode.USER_PROBLEM_LOG_NOT_FOUND;
    public UserProblemLogNotFoundException() {
        super(errorCode);
    }

    public UserProblemLogNotFoundException(Exception exception) {
        super(errorCode, exception);
    }
}
