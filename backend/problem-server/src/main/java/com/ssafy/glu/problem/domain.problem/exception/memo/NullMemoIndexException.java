package com.ssafy.glu.problem.domain.problem.exception.memo;

import com.ssafy.glu.problem.global.error.ErrorCode;
import com.ssafy.glu.problem.global.error.ServiceException;

public class NullMemoIndexException extends ServiceException {
	public NullMemoIndexException() {
		super(ErrorCode.NULL_MEMO_INDEX);
	}

	public NullMemoIndexException(Exception exception) {
		super(ErrorCode.NULL_MEMO_INDEX, exception);
	}
}
