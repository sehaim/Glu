
package com.ssafy.glu.problem.global.feign.dto;

public record UserProblemTypeResponse(
	Integer level,
	Integer score,
	ProblemTypeResponse type
) {
}
