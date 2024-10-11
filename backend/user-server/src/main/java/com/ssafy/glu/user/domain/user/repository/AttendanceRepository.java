package com.ssafy.glu.user.domain.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.glu.user.domain.user.domain.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>, AttendanceQueryRepository {

	Optional<Attendance> findFirstByUsersIdOrderByAttendanceDateDesc(Long userId);

	long countByUsersId(Long userId);

	// userId에 해당하는 모든 Attendance를 찾는 메서드
	List<Attendance> findAllByUsersId(Long userId);
}
