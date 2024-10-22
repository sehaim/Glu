package com.ssafy.glu.problem.domain.test.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ssafy.glu.problem.domain.problem.domain.UserProblemLog;
import com.ssafy.glu.problem.global.shared.BaseTimeDocument;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Document
@Getter
@ToString
public class Test extends BaseTimeDocument {
	@Id
	private String testId;

	private Integer correctCount;

	private Integer totalSolveTime;

	private Long userId;

	private List<String> userProblemLogIdList;

	public Test() {
	}

	@Builder
	public Test(Integer correctCount, Integer totalSolveTime, Long userId, List<String> userProblemLogIdList) {
		this.correctCount = correctCount == null ? 0 : correctCount;
		this.totalSolveTime = totalSolveTime == null ? 0 : totalSolveTime;
		this.userId = userId;
		this.userProblemLogIdList = userProblemLogIdList == null ? new ArrayList<>() : userProblemLogIdList;
	}

	public void initTest(Integer correctCount) {
		this.correctCount = correctCount;
	}

	public void updateUserProblemLogIdList(UserProblemLog userProblemLog) {
		this.correctCount += userProblemLog.isCorrect() ? 1 : 0;
		this.userProblemLogIdList.add(userProblemLog.getUserProblemLogId());
	}
}
