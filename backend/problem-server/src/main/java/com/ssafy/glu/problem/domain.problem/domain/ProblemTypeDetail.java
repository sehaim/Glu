package com.ssafy.glu.problem.domain.problem.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Document
@Getter
@AllArgsConstructor
@Builder
@ToString
public class ProblemTypeDetail {
	@Id
	private final String problemTypeDetailCode;
	private final String name;
}