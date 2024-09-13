package com.ssafy.glu.problem.domain.problem.repository;

import static com.ssafy.glu.problem.domain.problem.domain.QProblem.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSearchCondition;

public class ProblemQueryRepositoryImpl extends QuerydslRepositorySupport implements ProblemQueryRepository {

	public ProblemQueryRepositoryImpl(MongoOperations operations) {
		super(operations);
	}

	@Override
	public Page<Problem> findByCondition(ProblemSearchCondition condition, Pageable pageable) {
		return from(problem)
			.where(
				levelEq(condition.problemLevelCode()),
				typeEq(condition.problemTypeCode()),
				detailTypeEq(condition.problemTypeDetailCode())
			)
			.fetchPage(pageable);
	}

	public BooleanExpression levelEq(String problemLevelCode) {
		return problemLevelCode != null ? problem.problemLevel.problemLevelCode.eq(problemLevelCode) : null;
	}

	public BooleanExpression typeEq(String problemTypeCode) {
		return problemTypeCode != null ? problem.problemType.problemTypeCode.eq(problemTypeCode) : null;
	}

	public BooleanExpression detailTypeEq(String problemDetailTypeCode) {
		return problemDetailTypeCode != null ?
			problem.problemTypeDetail.problemTypeDetailCode.eq(problemDetailTypeCode) : null;
	}
}
