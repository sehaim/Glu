package com.ssafy.glu.auth.domain.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.glu.auth.domain.auth.domain.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

	Optional<Users> findByLoginIdAndPassword(String loginId, String password);

}
