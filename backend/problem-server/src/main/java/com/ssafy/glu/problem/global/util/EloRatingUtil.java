package com.ssafy.glu.problem.global.util;

public class EloRatingUtil {
    private static final int K = 32; // Standard value for K-factor

    private static double calculateExpectedScore(int userScore, int problemScore) {
        return 1.0 / (1 + Math.pow(10, (problemScore - userScore) / 100.0));
    }
    // 실제 문제 풀이 결과에 따른 새로운 점수 계산
    public static int calculateNewScore(int userScore, int problemScore, boolean isCorrect) {
        double expectedScore = calculateExpectedScore(userScore, problemScore);
        double actualScore = isCorrect ? 1 : 0;  // 맞췄으면 1, 틀렸으면 0

        // 새 점수 = 기존 점수 + K * (실제 점수 - 예상 점수)
        return (int) (userScore + K * (actualScore - expectedScore));
    }
}