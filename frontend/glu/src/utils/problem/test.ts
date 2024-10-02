import { GetServerSidePropsContext } from 'next';
import { createAuthAxios } from '../common';

interface ProblemSolveRequest {
  problemId: string;
  userAnswer: string;
  solvedTime: number;
}

// 종합 테스트 문제 생성
export const getRecommendedTestProblemsAPI = async (
  context: GetServerSidePropsContext,
) => {
  try {
    const authAxios = createAuthAxios(context);
    const res = await authAxios.get(`/recommend/test/general`);

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

    // 커스텀 응답에서 httpStatus 확인
    if (res.data.httpStatus === 400) {
      throw new Error(res.data.detailMessage || '잘못된 요청입니다.');
    }

    return res;
  } catch (error) {
    throw new Error('문제 채점 중 문제가 발생했습니다.');
  }
};
