package com.ssafy.glu.user.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.glu.user.domain.user.domain.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>, AttendanceQueryRepository {

}
