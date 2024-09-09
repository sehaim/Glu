package com.ssafy.glu.user.domain.user.repository;

import static com.ssafy.glu.user.domain.user.domain.QAttendance.*;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.glu.user.domain.user.dto.request.AttendanceRequest;
import com.ssafy.glu.user.domain.user.dto.response.AttendanceResponse;

import jakarta.persistence.EntityManager;

public class AttendanceQueryRepositoryImpl implements AttendanceQueryRepository {

	private final JPAQueryFactory queryFactory;

	public AttendanceQueryRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<AttendanceResponse> countAttendanceByYearAndMonth(Long userId, AttendanceRequest request) {

		// 이번 달의 시작일과 마지막일 설정
		YearMonth yearMonth = YearMonth.of(request.year(), request.month());
		LocalDateTime startDateTime = yearMonth.atDay(1).atStartOfDay();
		LocalDateTime endDateTime = yearMonth.atEndOfMonth().atTime(23, 59, 59);

		return queryFactory
			.select(attendance.attendanceDate, attendance.todaySolve)
			.from(attendance)
			.where(
				usernameEq(userId),
				attendanceDateBetween(startDateTime, endDateTime)
			)
			.orderBy(attendance.attendanceDate.asc()) // 날짜 순으로 정렬
			.groupBy(attendance.attendanceDate)
			.fetch()
			.stream()
			.map(AttendanceResponse::of)
			.collect(Collectors.toList());
	}

	private BooleanExpression usernameEq(Long userId) {
		return isEmpty(userId) ? null : attendance.users.id.eq(userId);
	}

	private BooleanExpression attendanceDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		return attendance.attendanceDate.between(startDateTime, endDateTime);
	}
}
