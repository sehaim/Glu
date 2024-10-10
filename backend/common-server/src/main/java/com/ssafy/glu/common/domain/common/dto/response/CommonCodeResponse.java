package com.ssafy.glu.common.domain.common.dto.response;

import java.util.List;

import com.ssafy.glu.common.domain.common.domain.CommonCode;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "공통 코드 DTO")
public record CommonCodeResponse(
	@Schema(description = "코드 타입", example = "PT01")
	String typeCode,

	@Schema(description = "코드 이름", example = "문제 유형")
	String name,

	@Schema(description = "코드 상세 목록")
	List<CommonCodeResponse> codeDetailList
) {
	public static CommonCodeResponse of(CommonCode typeCode, List<CommonCodeResponse> codeDetailList) {
		if (typeCode == null || codeDetailList == null)
			return null;
		return CommonCodeResponse.builder()
			.typeCode(typeCode.getTypeCode())
			.name(typeCode.getName())
			.codeDetailList(codeDetailList)
			.build();
	}

	public static CommonCodeResponse of(String typeCode, String name, List<CommonCodeResponse> typeDetailList) {
		if (typeCode == null || name == null)
			return null;
		return of(typeCode, name, typeDetailList);
	}
}
