package com.ssafy.glu.problem.global.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EloRatingUtilTest {
	@Test
	public void testCalculateNewScore(){
	    //given
		int userScore = 350;
		int problemScore = 400;
		boolean isCorrect = true;

		//when
		int newScore = EloRatingUtil.calculateNewScore(userScore, problemScore, isCorrect);

	    //then
		assertThat(newScore).isGreaterThan(userScore);
	}
}