package com.ssafy.glu.problem.domain.problem.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.ProblemLevelCode;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeCode;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeDetailCode;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.support.QuerydslRepositorySupport;

import static com.ssafy.glu.problem.domain.problem.domain.QProblem.problem;

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

    public BooleanExpression levelEq(ProblemLevelCode problemLevelCode) {
        return problemLevelCode != null ? problem.problemLevelCode.eq(problemLevelCode) : null;
    }

    public BooleanExpression typeEq(ProblemTypeCode problemTypeCode) {
        return problemTypeCode != null ? problem.problemTypeCode.eq(problemTypeCode) : null;
    }

    public BooleanExpression detailTypeEq(ProblemTypeDetailCode problemDetailTypeCode) {
        return problemDetailTypeCode != null ? problem.problemTypeDetailCode.eq(problemDetailTypeCode) : null;
    }
}
