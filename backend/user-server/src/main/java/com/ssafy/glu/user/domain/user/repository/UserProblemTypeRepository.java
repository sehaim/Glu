package com.ssafy.glu.user.domain.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.glu.user.domain.user.domain.UserProblemType;

public interface UserProblemTypeRepository extends JpaRepository<UserProblemType, Long> {

	//유저 문제 타입 조회
	List<UserProblemType> findAllByUserId(Long userId);

}
