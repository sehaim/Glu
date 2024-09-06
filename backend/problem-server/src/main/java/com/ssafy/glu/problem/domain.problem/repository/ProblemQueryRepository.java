package com.ssafy.glu.problem.domain.problem.repository;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProblemQueryRepository{
    Page<Problem> findByCondition(ProblemSearchCondition condition, Pageable pageable);
}