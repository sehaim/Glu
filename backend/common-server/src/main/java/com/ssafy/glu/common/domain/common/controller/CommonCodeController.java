package com.ssafy.glu.common.domain.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.glu.common.domain.common.dto.response.CommonCodeResponse;
import com.ssafy.glu.common.domain.common.service.CommonCodeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common")
public class CommonCodeController {
	private final CommonCodeService commonCodeService;

	@GetMapping
	public ResponseEntity<CommonCodeResponse> getCommonCodeList(@RequestParam("codeType") String codeType) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(commonCodeService.getCommonCodeListByTypeCodeWithDetails(codeType));
	}
}
