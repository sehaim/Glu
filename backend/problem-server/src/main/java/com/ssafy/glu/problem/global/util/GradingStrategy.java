package com.ssafy.glu.problem.global.util;

@FunctionalInterface
public interface GradingStrategy {
	boolean isCorrect(String answer, String correctAnswer);
}