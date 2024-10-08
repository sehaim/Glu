import { GetServerSidePropsContext } from 'next';
import { setCookie } from 'cookies-next';
import { createAuthAxios, refreshUserAPI } from '../common';

interface ProblemSolveRequest {
  problemId: string;
  userAnswer: string;
  solvedTime: number;
}

// 레벨 테스트 문제 생성
export const getRecommendedLevelTestProblemsAPI = async (
  context: GetServerSidePropsContext,
) => {
  try {
    const authAxios = createAuthAxios(context);
    const res = await authAxios.get(`recommend/test/level`);

    // 커스텀 응답에서 httpStatus 확인
    if (res.data.httpStatus === 400) {
      throw new Error(res.data.detailMessage || '잘못된 요청입니다.');
    }

    return res;
  } catch (error) {
    throw new Error('종합 테스트 문제를 가져오는 중 문제가 발생했습니다.');
  }
};

// 종합 테스트 문제 생성
export const getRecommendedTestProblemsAPI = async (
  context: GetServerSidePropsContext,
) => {
  try {
    const authAxios = createAuthAxios(context);
    const res = await authAxios.get(`recommend/test/general`);

    // 커스텀 응답에서 httpStatus 확인
    if (res.data.httpStatus === 400) {
      throw new Error(res.data.detailMessage || '잘못된 요청입니다.');
    }

    return res;
  } catch (error) {
    throw new Error('종합 테스트 문제를 가져오는 중 문제가 발생했습니다.');
  }
};

// 테스트 문제 채점
export const postTestProblemGradingAPI = async (
  totalSolvedTime: number,
  problemSolveRequestList: ProblemSolveRequest[],
) => {
  try {
    const authAxios = createAuthAxios();
    const res = await authAxios.post(`tests/grading`, {
      totalSolvedTime,
      problemSolveRequestList,
    });

    const newToken = await refreshUserAPI();
    setCookie('accessToken', newToken, {
      maxAge: 60 * 60 * 24 * 14,
      path: '/',
    });

    // 커스텀 응답에서 httpStatus 확인
    if (res.data.httpStatus === 400) {
      throw new Error(res.data.detailMessage || '잘못된 요청입니다.');
    }

    return res;
  } catch (error) {
    throw new Error('문제 채점 중 문제가 발생했습니다.');
  }
};

// 종합 테스트 결과 조회
export const getTestResultAPI = async (
  context: GetServerSidePropsContext,
  testId: string,
) => {
  try {
    const authAxios = createAuthAxios(context);
    const res = await authAxios.get(`tests/${testId}`);

    // 커스텀 응답에서 httpStatus 확인
    if (res.data.httpStatus === 400) {
      throw new Error(res.data.detailMessage || '잘못된 요청입니다.');
    }

    return res;
  } catch (error) {
    throw new Error('종합 테스트 결과 조회 중 문제가 발생했습니다.');
  }
};
