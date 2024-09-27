package com.ssafy.glu.problem.domain.test.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.glu.problem.domain.test.domain.Test;

@Repository
public interface TestRepository extends MongoRepository<Test, String> {
    Page<Test> findByUserId(Long userId, Pageable pageable);
}
