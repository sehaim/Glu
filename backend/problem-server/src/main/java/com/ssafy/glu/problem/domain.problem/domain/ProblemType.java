package com.ssafy.glu.problem.domain.problem.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Document
@Getter
@AllArgsConstructor
public class ProblemType {
	@Id
	private String problemTypeCode;
	private String name;
}