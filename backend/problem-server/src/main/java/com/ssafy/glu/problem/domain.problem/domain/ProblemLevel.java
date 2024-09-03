package com.ssafy.glu.problem.domain.problem.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Document
@Getter
@Builder
@AllArgsConstructor
public class ProblemLevel {
	@Id
	private String problemLevelCode;
	private String name;
}
