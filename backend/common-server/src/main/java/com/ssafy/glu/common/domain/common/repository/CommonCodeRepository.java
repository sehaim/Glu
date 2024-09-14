package com.ssafy.glu.common.domain.common.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssafy.glu.common.domain.common.domain.CommonCode;

public interface CommonCodeRepository extends MongoRepository<CommonCode, String> {
	// 특정 typeCode와 일치하는 공통 코드를 찾는 메소드
	CommonCode findByTypeCode(String typeCode);

	// 특정 부모 코드를 기준으로 하위 코드를 찾는 메소드
	List<CommonCode> findByParentCodeTypeCode(String parentCodeTypeCode);
}
