package com.ssafy.glu.user.global.util;

@FunctionalInterface
public interface GradingStrategy {
	boolean isCorrect(String answer, String correctAnswer);
}