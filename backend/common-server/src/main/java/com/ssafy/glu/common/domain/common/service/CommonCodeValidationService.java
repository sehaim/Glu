package com.ssafy.glu.common.domain.common.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.mongodb.MongoWriteException;
import com.ssafy.glu.common.domain.common.domain.CommonCode;
import com.ssafy.glu.common.domain.common.dto.response.CommonCodeResponse;
import com.ssafy.glu.common.domain.common.exception.CommonCodeFetchFailedException;
import com.ssafy.glu.common.domain.common.exception.CommonCodeNotFoundException;
import com.ssafy.glu.common.domain.common.exception.NullSearchCodeException;
import com.ssafy.glu.common.domain.common.repository.CommonCodeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Primary
public class CommonCodeValidationService implements CommonCodeService {
	private final CommonCodeService commonCodeService;
	private final CommonCodeRepository commonCodeRepository;

	@Override
	public CommonCodeResponse getCommonCodeListByTypeCodeWithDetails(String searchTypeCode) {
		// Null 값 검증
		validateSearchTypeCode(searchTypeCode);

		// Code 존재 여부 확인
		validateCommonCode(searchTypeCode);

		try {
			return commonCodeService.getCommonCodeListByTypeCodeWithDetails(searchTypeCode);
		} catch (MongoWriteException exception) {
			throw new CommonCodeFetchFailedException(exception);
		}
	}

	@Override
	public CommonCodeResponse buildCommonCodeResponse(CommonCode parentCode) {
		return commonCodeService.buildCommonCodeResponse(parentCode);
	}

	private void validateSearchTypeCode(String searchTypeCode) {
		if (searchTypeCode == null) {
			throw new NullSearchCodeException();
		}
	}

	private void validateCommonCode(String searchTypeCode) {
		if (commonCodeRepository.findByTypeCode(searchTypeCode) == null) {
			throw new CommonCodeNotFoundException();
		}
	}
}
