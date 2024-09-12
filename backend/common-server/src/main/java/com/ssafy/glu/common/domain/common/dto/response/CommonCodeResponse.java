package com.ssafy.glu.common.domain.common.dto.response;

import java.util.List;

import com.ssafy.glu.common.domain.common.domain.CommonCode;

import lombok.Builder;

@Builder
public record CommonCodeResponse(
	String typeCode,
	String name,
	List<CommonCodeResponse> typeDetailList
) {
	public static CommonCodeResponse of(CommonCode typeCode, List<CommonCodeResponse> typeDetailList) {
		if (typeCode == null || typeDetailList == null)
			return null;
		return CommonCodeResponse.builder()
			.typeCode(typeCode.getTypeCode())
			.name(typeCode.getName())
			.typeDetailList(typeDetailList)
			.build();
	}

	public static CommonCodeResponse of(String typeCode, String name, List<CommonCodeResponse> typeDetailList) {
		if (typeCode == null || name == null)
			return null;
		return of(typeCode, name, typeDetailList);
	}
}
