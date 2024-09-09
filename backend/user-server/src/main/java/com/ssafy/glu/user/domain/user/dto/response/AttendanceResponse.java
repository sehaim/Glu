package com.ssafy.glu.user.domain.user.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.querydsl.core.Tuple;

public record AttendanceResponse(
	LocalDate date,
	Integer totalSolvedProblemCnt) {
	public static AttendanceResponse of(Tuple tuple) {
		LocalDateTime dateTime = tuple.get(0, LocalDateTime.class);
		LocalDate date = (dateTime != null) ? dateTime.toLocalDate() : null;
		Integer solve = tuple.get(1, Integer.class);

		return new AttendanceResponse(date, solve);
	}
}
