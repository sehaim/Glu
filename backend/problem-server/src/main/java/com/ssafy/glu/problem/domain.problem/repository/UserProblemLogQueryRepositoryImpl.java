package com.ssafy.glu.problem.domain.problem.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.dto.request.UserProblemLogSearchCondition;
import com.ssafy.glu.problem.global.util.CountResult;

public class UserProblemLogQueryRepositoryImpl implements UserProblemLogQueryRepository {
	private final MongoTemplate template;

	public UserProblemLogQueryRepositoryImpl(MongoTemplate template) {
		this.template = template;
	}

	@Override
	public Page<Problem> findByCondition(Long userId, UserProblemLogSearchCondition condition, Pageable pageable) {
		// 검색 조건 빌딩
		Criteria criteria = new Criteria();
		userIdEq(criteria, userId);

		Criteria lastCriteria = new Criteria();
		statusEq(lastCriteria, condition.status());
		problemTypeEq(lastCriteria, condition.problemTypeCode());

		// 집계 파이프라인
		Aggregation aggregation = Aggregation.newAggregation(
				matchByCriteria(criteria),
				sortByCreatedDate(),
				groupByProblemId(),
				matchByCriteria(lastCriteria),
				lookupProblem(),
				unwindProblem(),
				projectProblem(),
				skipPagination(pageable.getOffset()),
				limitPagination(pageable.getPageSize())
		);

		// 문제 리스트 가져오기
		AggregationResults<Problem> results = template.aggregate(aggregation, "userProblemLog", Problem.class);
		List<Problem> problems = results.getMappedResults();

		// 전체 데이터 수를 계산하기 위해 집계 쿼리 실행
		Aggregation countAggregation = Aggregation.newAggregation(
				matchByCriteria(criteria),
				sortByCreatedDate(),
				groupByProblemId(),
				matchByCriteria(lastCriteria),
				Aggregation.count().as("count")
		);

		AggregationResults<CountResult> countResults = template.aggregate(countAggregation, "userProblemLog", CountResult.class);
		long total = countResults.getMappedResults().stream().mapToLong(CountResult::getCount).sum();

		return new PageImpl<>(problems, pageable, total);
	}

	MatchOperation matchByCriteria(Criteria criteria) {
		return Aggregation.match(criteria);
	}

	SortOperation sortByCreatedDate() {
		return Aggregation.sort(Sort.by(Sort.Direction.DESC, "createdDate"));
	}

	GroupOperation groupByProblemId() {
		return Aggregation.group("problemId")
				.first("problemId").as("problemId")
				.first("isCorrect").as("isCorrect");
	}

	LookupOperation lookupProblem() {
		return Aggregation.lookup("problem", "problemId", "_id", "problem");
	}

	UnwindOperation unwindProblem() {
		return Aggregation.unwind("problem");
	}

	ProjectionOperation projectProblem() {
		return Aggregation.project("problem");
	}

	AggregationOperation skipPagination(long offset) {
		return Aggregation.skip(offset);
	}

	AggregationOperation limitPagination(int size) {
		return Aggregation.limit(size);
	}

	private void userIdEq(Criteria criteria, Long userId) {
		if(userId != null) criteria.and("userId").is(userId);
	}

	private void statusEq(Criteria criteria, Problem.Status status) {
		if (status != null) criteria.and("isCorrect").is(Problem.Status.CORRECT.equals(status));
	}

	private void problemTypeEq(Criteria criteria, String problemTypeCode) {
		if(problemTypeCode != null) criteria.and("problem.problemTypeCode").is(problemTypeCode);
	}

}
