package com.ssafy.glu.user.domain.user.dto.request;

import java.util.List;
import java.util.Map;

public record ExpUpdateRequest(
	Map<String, Integer> userProblemTypeLevels, // "userProblemTypeLevels" 필드
	List<ProblemInfo> problemInfoList // "problemInfoList" 필드
) {
	// 내부 ProblemInfo 클래스도 record로 정의
	public static record ProblemInfo(
		int level,  // "level" 필드
		String code // "code" 필드
	) {}
}
