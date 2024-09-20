package com.ssafy.glu.problem.domain.problem.dto.grading;

import static com.ssafy.glu.problem.global.util.ScoreUtil.*;

import com.ssafy.glu.problem.global.util.ScoreUtil;

import lombok.Getter;

@Getter
public class ProblemTypeScore {
    int score;
    int level;

    // 점수 업데이트 후 새로운 ProblemTypeScore 반환
    public void updateScore(int acquiredScore) {
        this.score += acquiredScore;
        if(this.score >= REQUIRED_SCORE){
            int levelAdded = this.score / REQUIRED_SCORE;
            this.score -= levelAdded * REQUIRED_SCORE;
            this.level += levelAdded;
        }
    }
    // 특정 문제 유형의 총 점수 가져오기
    public int getTotalScore() {
        return ScoreUtil.calculateTotalScore(level,score);
    }
}