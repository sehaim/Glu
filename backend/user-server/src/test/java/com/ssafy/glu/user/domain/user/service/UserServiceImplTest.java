package com.ssafy.glu.user.domain.user.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ssafy.glu.user.domain.user.domain.UserProblemType;
import com.ssafy.glu.user.domain.user.domain.Users;
import com.ssafy.glu.user.domain.user.dto.request.UserRegisterRequest;
import com.ssafy.glu.user.domain.user.dto.request.UserUpdateRequest;
import com.ssafy.glu.user.domain.user.dto.response.UserResponse;
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
	// @Rollback(false)
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

	@Test
	void getUser() {
		// Given
		UserRegisterRequest registerRequestDTO = new UserRegisterRequest("id1234", "1234", "ssafy", LocalDate.of(2000, 1, 1));
		Long id = userService.register(registerRequestDTO);

		// When
		UserResponse user = userService.getUser(id);

		// Then
		assertEquals(id, user.id(), "User id should match");
		assertEquals("ssafy", user.nickname(), "Nickname should match");
		assertNotNull(user.problemTypeList(), "ProblemTypeList should not be null");
		assertEquals(3, user.problemTypeList().size(), "User should have 3 problem types");
	}

	@Test
	void updateUser() {
		// Given
		UserRegisterRequest registerRequestDTO = new UserRegisterRequest("id1234", "1234", "ssafy", LocalDate.of(2000, 1, 1));
		Long id = userService.register(registerRequestDTO);

		// When
		UserUpdateRequest updateRequest = new UserUpdateRequest("newNickname", "1234", "12345");
		userService.updateUser(id, updateRequest);

		Users findUser = userRepository.findById(id).orElseThrow();

		// Then
		assertEquals("newNickname", findUser.getNickname(), "Nickname should be updated");
		assertTrue(passwordEncoder.matches(updateRequest.newPassword(), findUser.getPassword()), "Password should match");
	}

	@Test
	void deleteUser() {
		// Given
		UserRegisterRequest registerRequestDTO = new UserRegisterRequest("id1234", "1234", "ssafy", LocalDate.of(2000, 1, 1));
		Long id = userService.register(registerRequestDTO);

		// When
		userService.deleteUser(id);

		// Then
		Users deletedUser = userRepository.findById(id).orElseThrow();
		assertTrue(deletedUser.getIsDeleted(), "User should be marked as deleted");
	}

}