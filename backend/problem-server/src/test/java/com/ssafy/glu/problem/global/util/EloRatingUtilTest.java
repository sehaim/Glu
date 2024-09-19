package com.ssafy.glu.problem.global.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class EloRatingUtilTest {
	@Test
	public void testCalculateNewScoreWhenCorrect(){
	    //given
		int userScore = 350;
		int problemScore = 400;
		boolean isCorrect = true;

		//when
		int newScore = EloRatingUtil.calculateNewScore(userScore, problemScore, isCorrect);

	    //then
		assertThat(newScore).isGreaterThan(userScore);
		log.info("(user : {}, problem : {}, correct : {}) {} -> {}", userScore, problemScore, isCorrect, userScore, newScore);
	}
	@Test
	public void testCalculateNewScoreWhenCorrect2(){
		//given
		int userScore = 0;
		int problemScore = 700;
		boolean isCorrect = true;

		//when
		int newScore = EloRatingUtil.calculateNewScore(userScore, problemScore, isCorrect);

		//then
		assertThat(newScore).isGreaterThan(userScore);
		log.info("(user : {}, problem : {}, correct : {}) {} -> {}", userScore, problemScore, isCorrect, userScore, newScore);
	}
	@Test
	public void testCalculateNewScoreWhenWrong(){
		//given
		int userScore = 350;
		int problemScore = 400;
		boolean isCorrect = false;

		//when
		int newScore = EloRatingUtil.calculateNewScore(userScore, problemScore, isCorrect);

		//then
		assertThat(newScore).isLessThan(userScore);
		log.info("(user : {}, problem : {}, correct : {}) {} -> {}", userScore, problemScore, isCorrect, userScore, newScore);
	}
}