package com.ssafy.glu.user.domain.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.glu.user.domain.user.domain.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

	Boolean existsByLoginId(String loginId);

	List<Users> findAll();
}
