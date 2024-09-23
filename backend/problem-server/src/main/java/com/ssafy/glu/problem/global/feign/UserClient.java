package com.ssafy.glu.problem.global.feign;

import static com.ssafy.glu.problem.global.util.HeaderUtil.*;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ssafy.glu.problem.global.feign.dto.StageResponse;
import com.ssafy.glu.problem.global.feign.dto.UpdateStateRequest;
import com.ssafy.glu.problem.global.feign.dto.UserResponse;

@FeignClient(name = "glu-user", url = "${user.service.domain.user.url}")
public interface UserClient {
	@GetMapping
	ResponseEntity<UserResponse> getUser(@RequestHeader(USER_ID) Long userId);

	@PutMapping("/exp")
	ResponseEntity<StageResponse> updateExp(@RequestHeader(USER_ID) Long userId, UpdateStateRequest request);
}