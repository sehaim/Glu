package com.ssafy.glu.problem.domain.test.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssafy.glu.problem.domain.test.domain.Test;

public interface TestRepository extends MongoRepository<Test, String> {
}
