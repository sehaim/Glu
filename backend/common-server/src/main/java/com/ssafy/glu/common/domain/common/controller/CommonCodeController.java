package com.ssafy.glu.common.domain.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.glu.common.domain.common.dto.response.CommonCodeResponse;
import com.ssafy.glu.common.domain.common.service.CommonCodeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "공통 코드", description = "공통 코드 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common")
public class CommonCodeController {
	private final CommonCodeService commonCodeService;

	@Operation(summary = "공통 코드 목록 조회", description = "주어진 코드 타입에 대한 공통 코드 목록을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "공통 코드 목록 조회 성공",
			content = @Content(schema = @Schema(implementation = CommonCodeResponse.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", description = "공통 코드를 찾을 수 없습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping
	public ResponseEntity<CommonCodeResponse> getCommonCodeList(@RequestParam("codeType") String codeType) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(commonCodeService.getCommonCodeListByTypeCodeWithDetails(codeType));
	}
}
