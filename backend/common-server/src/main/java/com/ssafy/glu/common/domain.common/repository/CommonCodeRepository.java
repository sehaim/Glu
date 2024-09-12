package com.ssafy.glu.common.domain.common.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssafy.glu.common.domain.common.domain.CommonCode;

public interface CommonCodeRepository extends MongoRepository<CommonCode, String> {
}
