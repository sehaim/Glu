package com.ssafy.glu.user.domain.user.controller;

import static com.ssafy.glu.user.global.util.HeaderUtil.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.glu.user.domain.user.dto.request.UserRegisterRequest;
import com.ssafy.glu.user.domain.user.dto.request.UserUpdateRequest;
import com.ssafy.glu.user.domain.user.dto.response.UserResponse;
import com.ssafy.glu.user.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<Void> register(UserRegisterRequest userRegisterRequest) {
		userService.register(userRegisterRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping
	public ResponseEntity<UserResponse> getUser(@RequestHeader(USER_ID) Long userId) {
		UserResponse user = userService.getUser(userId);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@PutMapping
	public ResponseEntity<Void> updateUser(@RequestHeader(USER_ID) Long userId, UserUpdateRequest userUpdateRequest) {
		userService.updateUser(userId, userUpdateRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteUser(@RequestHeader(USER_ID) Long userId) {
		userService.deleteUser(userId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
