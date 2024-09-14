package com.ssafy.glu.common.domain.common.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@Document
public class CommonCode {
	@Id
	private String typeCode;
	private String name;

	@Field("parent_code")
	@DocumentReference
	private CommonCode parentCode;
}
