package com.ssafy.glu.user.domain.user.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.glu.user.domain.user.domain.Attendance;
import com.ssafy.glu.user.domain.user.domain.UserProblemType;
import com.ssafy.glu.user.domain.user.domain.Users;
import com.ssafy.glu.user.domain.user.dto.request.ExpUpdateRequest;
import com.ssafy.glu.user.domain.user.dto.request.UserRegisterRequest;
import com.ssafy.glu.user.domain.user.dto.request.UserUpdateRequest;
import com.ssafy.glu.user.domain.user.dto.response.AttendanceResponse;
import com.ssafy.glu.user.domain.user.dto.response.ExpUpdateResponse;
import com.ssafy.glu.user.domain.user.dto.response.UserResponse;
import com.ssafy.glu.user.domain.user.repository.AttendanceRepository;
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
	AttendanceRepository attendanceRepository;
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
		assertEquals("levelImg1.png", user.imageUrl(), "Image url should match");
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
		UserUpdateRequest updateRequest = new UserUpdateRequest("newNickname", "1234", "12345", LocalDate.parse("2009-05-12"));
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

	// @Transactional
	// @Test
	// void getAttendance() {
	// 	// Given
	// 	UserRegisterRequest registerRequestDTO = new UserRegisterRequest("id1234", "1234", "ssafy", LocalDate.of(2000, 1, 1));
	// 	Long id = userService.register(registerRequestDTO);
	//
	// 	Users findUser = userRepository.findById(id).orElseThrow();
	//
	// 	Attendance attendance1 = Attendance.builder()
	// 		.users(findUser)
	// 		.attendanceDate(LocalDateTime.now())
	// 		.todaySolve(10)
	// 		.build();
	//
	// 	Attendance attendance2 = Attendance.builder()
	// 		.users(findUser)
	// 		.attendanceDate(LocalDate.now().atStartOfDay())
	// 		.todaySolve(30)
	// 		.build();
	//
	// 	Attendance attendance3 = Attendance.builder()
	// 		.users(findUser)
	// 		.attendanceDate(LocalDateTime.now().minusMonths(1))
	// 		.todaySolve(100)
	// 		.build();
	//
	// 	attendanceRepository.save(attendance1);
	// 	attendanceRepository.save(attendance2);
	// 	attendanceRepository.save(attendance3);
	//
	// 	// When
	// 	List<AttendanceResponse> attendanceData = userService.getAttendance(id, new AttendanceRequest(LocalDate.now().getYear(), LocalDate.now().getMonthValue()));
	//
	// 	// Then
	// 	assertFalse(attendanceData.isEmpty(), "Attendance data should not be empty");
	// 	assertEquals(30, attendanceData.get(0).totalSolvedProblemCnt());
	// 	assertEquals(10, attendanceData.get(1).totalSolvedProblemCnt());
	// }

	@Transactional
	@Test
	void getAttendance() {
		// Given
		UserRegisterRequest registerRequestDTO = new UserRegisterRequest("id1234", "1234", "ssafy", LocalDate.of(2000, 1, 1));
		Long id = userService.register(registerRequestDTO);

		Users findUser = userRepository.findById(id).orElseThrow();

		Attendance attendance1 = Attendance.builder()
			.users(findUser)
			.attendanceDate(LocalDateTime.now())
			.todaySolve(10)
			.build();

		Attendance attendance2 = Attendance.builder()
			.users(findUser)
			.attendanceDate(LocalDate.now().atStartOfDay())
			.todaySolve(30)
			.build();

		Attendance attendance3 = Attendance.builder()
			.users(findUser)
			.attendanceDate(LocalDateTime.now().minusMonths(1))
			.todaySolve(100)
			.build();

		attendanceRepository.save(attendance1);
		attendanceRepository.save(attendance2);
		attendanceRepository.save(attendance3);

		// When
		List<AttendanceResponse> attendanceData = userService.getAttendance(id);

		// Then
		assertFalse(attendanceData.isEmpty(), "Attendance data should not be empty");
		assertEquals(10, attendanceData.get(0).totalSolvedProblemCnt());
		assertEquals(30, attendanceData.get(1).totalSolvedProblemCnt());
	}


	@Transactional
	@Test
	void UserCheckTest() {

		// Given
		UserRegisterRequest registerRequestDTO = new UserRegisterRequest("id1234", "1234", "ssafy", LocalDate.of(2000, 1, 1));
		Long id = userService.register(registerRequestDTO);

		//when
		Boolean result1 = userService.checkUser("id1234");
		Boolean result2 = userService.checkUser("id12345");

		//then
		assertTrue(result1);
		assertFalse(result2);
	}

	@Transactional
	@Test
	void 레벨업X() {

		// given: 사용자 등록 및 초기 설정
		UserRegisterRequest registerRequestDTO = new UserRegisterRequest("id1234", "1234", "ssafy", LocalDate.of(2000, 1, 1));
		Long id = userService.register(registerRequestDTO);

		UserResponse user = userService.getUser(id);

		// given: ExpUpdateRequest 생성
		Map<String, Integer> userProblemTypeLevels = Map.of(
			"PT01", 1,
			"PT02", 2,
			"PT03", 3
		);

		List<ExpUpdateRequest.ProblemInfo> problemInfos = List.of(
			new ExpUpdateRequest.ProblemInfo(2, "PT01"),
			new ExpUpdateRequest.ProblemInfo(3, "PT02"),
			new ExpUpdateRequest.ProblemInfo(1, "PT03")
		);

		ExpUpdateRequest expUpdateRequest = new ExpUpdateRequest(userProblemTypeLevels, problemInfos);

		// when: 경험치 업데이트 로직 실행
		ExpUpdateResponse response = userService.updateExp(id, expUpdateRequest);

		// then: 결과 검증
		Users updatedUser = userRepository.findById(id).orElseThrow();

		assertNotNull(updatedUser);
		assertFalse(response.isStageUp()); // 스테이지가 업데이트되었는지 확인
		assertNotNull(response.stageUpUrl()); // 적절한 이미지가 반환되었는지 확인
	}

	@Transactional
	@Test
	void 레벨업O() {

		// given: 사용자 등록 및 초기 설정
		UserRegisterRequest registerRequestDTO = new UserRegisterRequest("id1234", "1234", "ssafy", LocalDate.of(2000, 1, 1));
		Long id = userService.register(registerRequestDTO);

		// given: ExpUpdateRequest 생성
		Map<String, Integer> userProblemTypeLevels = Map.of(
			"PT01", 1,
			"PT02", 2,
			"PT03", 3
		);

		List<ExpUpdateRequest.ProblemInfo> problemInfos = new ArrayList<>();

		for(int i=0; i<40; i++) {
			problemInfos.add(new ExpUpdateRequest.ProblemInfo(3, "PT01"));
		}

		ExpUpdateRequest expUpdateRequest = new ExpUpdateRequest(userProblemTypeLevels, problemInfos);

		// when: 경험치 업데이트 로직 실행
		ExpUpdateResponse response = userService.updateExp(id, expUpdateRequest);

		// then: 결과 검증
		Users updatedUser = userRepository.findById(id).orElseThrow();

		assertNotNull(updatedUser);
		assertTrue(response.isStageUp()); // 스테이지가 업데이트되었는지 확인
		assertNotNull(response.stageUpUrl()); // 적절한 이미지가 반환되었는지 확인
	}
}