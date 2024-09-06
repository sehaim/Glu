package com.ssafy.glu.user.domain.user.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.ssafy.glu.user.domain.user.domain.UserProblemType;
import com.ssafy.glu.user.domain.user.domain.Users;
import com.ssafy.glu.user.domain.user.dto.request.UserRegisterRequest;
import com.ssafy.glu.user.domain.user.repository.UserProblemTypeRepository;
import com.ssafy.glu.user.domain.user.repository.UserRepository;

@SpringBootTest
class UserServiceImplTest {

	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserProblemTypeRepository userProblemTypeRepository;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;


	@Test
	@Rollback(false)
	void register() {
		// Given
		UserRegisterRequest userRegisterRequest = new UserRegisterRequest("id1234", "1234", "ssafy", LocalDate.of(2000, 1, 1));

		// When
		Long id = userService.register(userRegisterRequest);

		// Then
		Users savedUser = userRepository.findById(id).orElseThrow();
		assertNotNull(savedUser, "User should be saved in the database");
		assertEquals("id1234", savedUser.getLoginId(), "Login ID should match");
		assertEquals("ssafy", savedUser.getNickname(), "Nickname should match");
		assertTrue(passwordEncoder.matches("1234", savedUser.getPassword()));
		assertEquals(LocalDate.of(2000, 1, 1), savedUser.getBirth(), "Birth date should match");

		List<UserProblemType> problemTypes = userProblemTypeRepository.findAllByUserId(id);
		assertEquals(3, problemTypes.size(), "User should have 3 problem types");
	}

}