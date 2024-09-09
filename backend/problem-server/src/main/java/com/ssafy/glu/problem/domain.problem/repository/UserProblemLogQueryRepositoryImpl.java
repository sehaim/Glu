package com.ssafy.glu.problem.domain.problem.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ReplaceRootOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.aggregation.VariableOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemLog;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSearchCondition;
import com.ssafy.glu.problem.global.util.CountResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserProblemLogQueryRepositoryImpl implements UserProblemLogQueryRepository {
	private final MongoTemplate template;

	public UserProblemLogQueryRepositoryImpl(MongoTemplate template) {
		this.template = template;
	}

	@Override
	public Optional<UserProblemLog> findFirstByUserIdAndProblem(Long userId, Problem problem) {
		Query query = new Query();

		// problemId는 ObjectId로 변환
		ObjectId problemObjectId = new ObjectId(problem.getProblemId());

		// userId는 Long으로 비교
		query.addCriteria(Criteria.where("userId").is(userId).and("problemId").is(problemObjectId));
		query.with(Sort.by(Sort.Direction.DESC, "createdDate"));

		log.info("===== Log 조회 - userId: {} and problemId: {} =====", userId, problemObjectId);
		return Optional.ofNullable(template.findOne(query, UserProblemLog.class));
	}

	@Override
	public Page<Problem> findAllProblemInLogByCondition(Long userId, ProblemSearchCondition condition,
		Pageable pageable) {
		// 검색 조건 빌딩
		Criteria criteria = new Criteria();
		userIdEq(criteria, userId);

		Criteria lastCriteria = new Criteria();
		statusEq(lastCriteria, condition.status());
		problemTypeEq(lastCriteria, condition.problemTypeCode());

		List<AggregationOperation> pipeline = new ArrayList<>();
		// userId로 검색
		pipeline.add(matchByCriteria(criteria));
		// 최근 순으로 정렬
		pipeline.add(sortByCreatedDate());
		// problemId로 그룹화
		pipeline.add(groupByProblemId());
		// problemId로 problem join
		pipeline.add(lookupProblem());
		// problem 배열 펼침
		pipeline.add(unwindProblem());
		// 문제 상태, 문제 유형에 따른 검색
		pipeline.add(matchByCriteria(lastCriteria));

		// 문제 메모 존재 여부
		if (condition.hasMemo() != null && condition.hasMemo()) {
			pipeline.add(lookupProblemMemoOperation());
			pipeline.add(matchNonEmptyMemo());
		}

		// count pipeline 저장
		List<AggregationOperation> countPipeline = new ArrayList<>(pipeline.stream().toList());

		// problem으로 root 변환
		pipeline.add(replaceRootToProblem());
		// sort 적용
		pipeline.add(sortByCondition(pageable.getSort()));
		// pagination 적용
		pipeline.add(skipPagination(pageable.getOffset()));
		pipeline.add(limitPagination(pageable.getPageSize()));

		// 집계 파이프라인
		Aggregation aggregation = Aggregation.newAggregation(pipeline);

		// 문제 리스트 가져오기
		AggregationResults<Problem> results = template.aggregate(aggregation, "userProblemLog", Problem.class);
		List<Problem> problems = results.getMappedResults();

		// 전체 데이터 수를 계산하기 위해 집계 쿼리 실행
		countPipeline.add(Aggregation.count().as("count"));
		Aggregation countAggregation = Aggregation.newAggregation(countPipeline);

		AggregationResults<CountResult> countResults = template.aggregate(countAggregation, "userProblemLog",
			CountResult.class);
		long total = countResults.getMappedResults().stream().mapToLong(CountResult::getCount).sum();

		return new PageImpl<>(problems, pageable, total);
	}

	MatchOperation matchByCriteria(Criteria criteria) {
		return Aggregation.match(criteria);
	}

	SortOperation sortByCondition(Sort sort) {
		return sort.isEmpty() ? sortByCreatedDate() : Aggregation.sort(sort);
	}

	SortOperation sortByCreatedDate() {
		return Aggregation.sort(Sort.by(Sort.Direction.DESC, "createdDate"));
	}

	GroupOperation groupByProblemId() {
		return Aggregation.group("problemId")
			.first("problemId")
			.as("problemId")
			.first("isCorrect")
			.as("isCorrect")
			.first("userId")
			.as("userId");
	}

	LookupOperation lookupProblem() {
		return Aggregation.lookup("problem", "problemId", "_id", "problem");
	}

	UnwindOperation unwindProblem() {
		return Aggregation.unwind("problem");
	}

	LookupOperation lookupProblemMemoOperation() {
		return Aggregation.lookup()
			.from("problemMemo") // target collection
			.let(VariableOperators.Let.ExpressionVariable.newVariable("userId").forField("$userId"),
				VariableOperators.Let.ExpressionVariable.newVariable("problemId").forField("$problemId"))
			.pipeline(Aggregation.match(Criteria.where("userId").is("$$userId").and("problemId").is("$$problemId")))
			.as("problemMemo");
	}

	MatchOperation matchNonEmptyMemo() {
		// Match to check if problemMemo is not empty
		return Aggregation.match(Criteria.where("problemMemo").not().size(0));
	}

	ReplaceRootOperation replaceRootToProblem() {
		return Aggregation.replaceRoot("problem");
	}

	AggregationOperation skipPagination(long offset) {
		return Aggregation.skip(offset);
	}

	AggregationOperation limitPagination(int size) {
		return Aggregation.limit(size);
	}

	private void userIdEq(Criteria criteria, Long userId) {
		if (userId != null)
			criteria.and("userId").is(userId);
	}

	private void statusEq(Criteria criteria, Problem.Status status) {
		if (status != null)
			criteria.and("isCorrect").is(Problem.Status.CORRECT.equals(status));
	}

	private void problemTypeEq(Criteria criteria, String problemTypeCode) {
		if (problemTypeCode != null)
			criteria.and("problem.problemType._id").is(problemTypeCode);
	}

}
