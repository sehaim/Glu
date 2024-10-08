package com.ssafy.glu.user.domain.user.service;

import com.ssafy.glu.user.domain.user.domain.ProblemTypeCode;
import com.ssafy.glu.user.domain.user.domain.UserProblemType;
import com.ssafy.glu.user.domain.user.domain.Users;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ScoreUpdateTest {

    @Test
    void testUpdateScore_FirstTimeForDifferentAges() {
        // 5세 테스트
        Users user5 = Users.builder()
                .birth(LocalDate.now().minusYears(5))
                .isFirst(true)
                .build();
        UserProblemType userProblemType5 = new UserProblemType(null, 1, 100, user5, ProblemTypeCode.PT01);
        userProblemType5.updateScore(50);
        assertEquals(50, userProblemType5.getScore()); // 100(초기값) + 50 + 90*1
        assertEquals(1, userProblemType5.getLevel());

        // 8세 테스트
        Users user8 = Users.builder()
                .birth(LocalDate.now().minusYears(8))
                .isFirst(true)
                .build();
        UserProblemType userProblemType8 = new UserProblemType(null, 1, 100, user8, ProblemTypeCode.PT01);
        userProblemType8.updateScore(50);
        assertEquals(20, userProblemType8.getScore()); // 100(초기값) + 50 + 90*3
        assertEquals(3, userProblemType8.getLevel());

        // 12세 테스트
        Users user12 = Users.builder()
                .birth(LocalDate.now().minusYears(12))
                .isFirst(true)
                .build();
        UserProblemType userProblemType12 = new UserProblemType(null, 1, 100, user12, ProblemTypeCode.PT01);
        userProblemType12.updateScore(50);
        assertEquals(80, userProblemType12.getScore()); // 100(초기값) + 50 + 90*7
        assertEquals(6, userProblemType12.getLevel());

        // 15세 테스트 (12세 초과)
        Users user15 = Users.builder()
                .birth(LocalDate.now().minusYears(15))
                .isFirst(true)
                .build();
        UserProblemType userProblemType15 = new UserProblemType(null, 1, 100, user15, ProblemTypeCode.PT01);
        userProblemType15.updateScore(50);
        assertEquals(80, userProblemType15.getScore()); // 100(초기값) + 50 + 90*7
        assertEquals(6, userProblemType15.getLevel());
    }

    @Test
    void testUpdateScore_NotFirstTime() {
        Users user = Users.builder()
                .birth(LocalDate.now().minusYears(25))
                .isFirst(false)
                .build();
        UserProblemType userProblemType = new UserProblemType(null, 2, 200, user, ProblemTypeCode.PT01);
        userProblemType.updateScore(50);
        assertEquals(50, userProblemType.getScore());
        assertEquals(2, userProblemType.getLevel());
    }

    @Test
    void testUpdateScore_MaxScore() {
        Users user = Users.builder()
                .birth(LocalDate.now().minusYears(25))
                .isFirst(false)
                .build();
        UserProblemType userProblemType = new UserProblemType(null, 7, 700, user, ProblemTypeCode.PT01);
        userProblemType.updateScore(150);
        assertEquals(100, userProblemType.getScore());
        assertEquals(7, userProblemType.getLevel());
    }

    @Test
    void testGetScore_BelowEightHundred() {
        Users user = Users.builder().build();
        UserProblemType userProblemType = new UserProblemType(null, 7, 750, user, ProblemTypeCode.PT01);
        assertEquals(50, userProblemType.getScore());
    }

    @Test
    void testGetScore_AboveEightHundred() {
        Users user = Users.builder().build();
        UserProblemType userProblemType = new UserProblemType(null, 7, 850, user, ProblemTypeCode.PT01);
        assertEquals(100, userProblemType.getScore());
    }

    @Test
    void testCalculateAge() {
        LocalDate birthDate = LocalDate.now().minusYears(25);
        assertEquals(25, UserProblemType.calculateAge(birthDate));
    }

    @Test
    void testCalculateUserLevel() {
        assertEquals(1, UserProblemType.calculateUserLevel(5));
        assertEquals(3, UserProblemType.calculateUserLevel(8));
        assertEquals(7, UserProblemType.calculateUserLevel(12));
        assertEquals(7, UserProblemType.calculateUserLevel(15));
    }
}