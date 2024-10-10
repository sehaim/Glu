package com.ssafy.glu.user.domain.user.controller;

import static com.ssafy.glu.user.global.util.HeaderUtil.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.glu.user.domain.user.dto.request.ExpUpdateRequest;
import com.ssafy.glu.user.domain.user.dto.request.UserRegisterRequest;
import com.ssafy.glu.user.domain.user.dto.request.UserUpdateRequest;
import com.ssafy.glu.user.domain.user.dto.response.AttendanceResponse;
import com.ssafy.glu.user.domain.user.dto.response.ExpUpdateResponse;
import com.ssafy.glu.user.domain.user.dto.response.UserResponse;
import com.ssafy.glu.user.domain.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@Tag(name = "사용자", description = "사용자 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@Operation(summary = "사용자 등록", description = "새로운 사용자를 등록합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "사용자 등록 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody UserRegisterRequest userRegisterRequest) {
		userService.register(userRegisterRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "사용자 정보 조회", description = "현재 로그인한 사용자의 정보를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공", content = @Content(schema = @Schema(implementation = UserResponse.class))),
		@ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping
	public ResponseEntity<UserResponse> getUser(@RequestHeader(USER_ID) Long userId) {
		UserResponse user = userService.getUser(userId);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@Operation(summary = "사용자 정보 수정", description = "현재 로그인한 사용자의 정보를 수정합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "사용자 정보 수정 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", description = "비밀번호를 틀렸습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PutMapping
	public ResponseEntity<Void> updateUser(@RequestHeader(USER_ID) Long userId, @RequestBody UserUpdateRequest userUpdateRequest) {
		userService.updateUser(userId, userUpdateRequest);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@Operation(summary = "사용자 삭제", description = "현재 로그인한 사용자의 계정을 삭제합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "사용자 삭제 성공"),
		@ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping
	public ResponseEntity<Void> deleteUser(@RequestHeader(USER_ID) Long userId) {
		userService.deleteUser(userId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Operation(summary = "사용자 ID 중복 확인", description = "새로운 사용자 ID의 중복 여부를 확인합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "중복 확인 성공", content = @Content(schema = @Schema(implementation = Boolean.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/check")
	public ResponseEntity<Boolean> checkUser(String id) {
		Boolean result = userService.checkUser(id);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@Operation(summary = "출석 정보 조회", description = "현재 로그인한 사용자의 출석 정보를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "출석 정보 조회 성공", content = @Content(schema = @Schema(implementation = AttendanceResponse.class))),
		@ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/attendance")
	public ResponseEntity<List<AttendanceResponse>> getAttendance(@RequestHeader(USER_ID) Long userId) {
		List<AttendanceResponse> result = userService.getAttendance(userId);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@Operation(summary = "경험치 업데이트", description = "현재 로그인한 사용자의 경험치를 업데이트합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "경험치 업데이트 성공", content = @Content(schema = @Schema(implementation = ExpUpdateResponse.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PutMapping("/exp")
	public ResponseEntity<ExpUpdateResponse> updateExp(@RequestHeader(USER_ID) Long userId, @RequestBody ExpUpdateRequest expUpdateRequest) {
		ExpUpdateResponse result = userService.updateExp(userId, expUpdateRequest);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}
