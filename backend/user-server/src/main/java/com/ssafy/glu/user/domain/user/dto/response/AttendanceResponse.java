package com.ssafy.glu.user.domain.user.dto.response;

import java.time.LocalDate;

import com.querydsl.core.Tuple;
import com.ssafy.glu.user.domain.user.domain.Attendance;

public record AttendanceResponse(
	LocalDate date,
	Integer totalSolvedProblemCnt) {
	public static AttendanceResponse of(Tuple tuple) {
		return new AttendanceResponse(
			tuple.get(0, LocalDate.class), // Tuple에서 첫 번째 필드를 LocalDate로 가져옵니다.
			tuple.get(1, Integer.class) // Tuple에서 두 번째 필드를 Integer로 가져옵니다.
		);
	}
}
