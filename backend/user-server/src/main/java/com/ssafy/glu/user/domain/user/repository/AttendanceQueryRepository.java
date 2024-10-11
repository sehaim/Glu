package com.ssafy.glu.user.domain.user.repository;

import java.util.List;

import com.ssafy.glu.user.domain.user.dto.request.AttendanceRequest;
import com.ssafy.glu.user.domain.user.dto.response.AttendanceResponse;

public interface AttendanceQueryRepository {

	public List<AttendanceResponse> countAttendanceByYearAndMonth(Long userId, AttendanceRequest request);

}
