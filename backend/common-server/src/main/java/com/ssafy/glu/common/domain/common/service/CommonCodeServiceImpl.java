package com.ssafy.glu.common.domain.common.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.glu.common.domain.common.domain.CommonCode;
import com.ssafy.glu.common.domain.common.dto.response.CommonCodeResponse;
import com.ssafy.glu.common.domain.common.repository.CommonCodeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonCodeServiceImpl implements CommonCodeService {
	private final CommonCodeRepository commonCodeRepository;

	public CommonCodeResponse getCommonCodeListByTypeCodeWithDetails(String typeCode) {
		CommonCode commonCode = commonCodeRepository.findByTypeCode(typeCode);

		return buildCommonCodeResponse(commonCode);
	}

	public CommonCodeResponse buildCommonCodeResponse(CommonCode parentCode) {
		List<CommonCode> childCodeList = commonCodeRepository.findByParentCodeTypeCode(parentCode.getTypeCode());

		List<CommonCodeResponse> childResponseList = childCodeList.isEmpty() ?
			List.of() :
			childCodeList.stream()
				.map(this::buildCommonCodeResponse)
				.collect(Collectors.toList());

		return CommonCodeResponse.of(parentCode, childResponseList);
	}
}
