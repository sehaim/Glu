package com.ssafy.glu.problem.global.util;

public class EloRatingUtil {
    private static final int K = 8; // 최대 얻거나 잃을 수 있는 점수

    private static double calculateExpectedCorrectRate(int userScore, int problemScore) {
        return 1.0 / (1 + Math.pow(10, (problemScore - userScore) / 400.0));
    }
    // 실제 문제 풀이 결과에 따른 새로운 점수 계산
    public static int calculateNewScore(int userScore, int problemScore, boolean isCorrect) {
        double expectedRate = calculateExpectedCorrectRate(userScore, problemScore); // 예상 맞출 확률
        double actualRate = isCorrect ? 1 : 0;  // 맞췄으면 1, 틀렸으면 0

        // 새 점수 = 기존 점수 + K * (실제 확률 - 예상 확률)
        return (int)Math.round(userScore + K * (actualRate - expectedRate));
    }
    // 실제 문제 풀이 결과에 따른 얻는 점수 계산
    public static int calculateAcquiredScore(int userScore, int problemScore, boolean isCorrect) {
        double expectedRate = calculateExpectedCorrectRate(userScore, problemScore); // 예상 맞출 확률
        double actualRate = isCorrect ? 1 : 0;  // 맞췄으면 1, 틀렸으면 0

        // 새 점수 = 기존 점수 + K * (실제 확률 - 예상 확률)
        return (int)Math.round(K * (actualRate - expectedRate));
    }
}