package com.ssafy.glu.problem.domain.problem.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Document
@Getter
@Builder
@AllArgsConstructor
@ToString
public class ProblemLevel {
	@Id
	private final String problemLevelCode;
	private final String name;
}
