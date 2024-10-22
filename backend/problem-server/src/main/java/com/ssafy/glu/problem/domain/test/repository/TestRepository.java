package com.ssafy.glu.problem.domain.test.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.glu.problem.domain.test.domain.Test;

@Repository
public interface TestRepository extends MongoRepository<Test, String> {
	Page<Test> findByUserId(Long userId, Pageable pageable);

	// 가장 최근에 응시한 Test 찾기
	Optional<Test> findTopByUserIdOrderByCreatedDateDesc(Long userId);
}
