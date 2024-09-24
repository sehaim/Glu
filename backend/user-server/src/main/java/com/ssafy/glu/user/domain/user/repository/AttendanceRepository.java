package com.ssafy.glu.user.domain.user.repository;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.glu.user.domain.user.domain.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>, AttendanceQueryRepository {

	Optional<Attendance> findFirstByUsersIdOrderByAttendanceDateDesc(Long userId);

	long countByUsersId(Long userId);
}
