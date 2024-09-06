package com.ssafy.glu.user.domain.user.controller;

import static com.ssafy.glu.user.global.util.HeaderUtil.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.glu.user.domain.user.dto.request.UserRegisterRequest;
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

	@GetMapping("/")
	public ResponseEntity<?> getUser(@RequestHeader(USER_ID) Long userId) {
		userService.getUser(userId);
		return ResponseEntity.ok("조회 완료");
	}

}
