import { authAxios } from '../common';

// 단일 문제 가져오기
export const getSingleProblem = async (problemId: number) => {
  try {
    const res = await authAxios.get(`/problems/${problemId}`);

    // 커스텀 응답에서 httpStatus 확인
    if (res.data.httpStatus === 400) {
      throw new Error(res.data.detailMessage || '잘못된 요청입니다.');
    }

    return res;
  } catch (error) {
    throw new Error('단일 문제를 가져오는 중 문제가 발생했습니다.');
  }
};

// 단일 문제 채점
export const postSingleProblemGrading = async (
  problemId: number,
  userAnswer: string,
  solvedTime: number,
) => {
  try {
    const res = await authAxios.post(`/problems/${problemId}/grading`, {
      userAnswer,
      solvedTime,
    });

    // 커스텀 응답에서 httpStatus 확인
    if (res.data.httpStatus === 400) {
      throw new Error(res.data.detailMessage || '잘못된 요청입니다.');
    }

    return res;
  } catch (error) {
    throw new Error('단일 문제 채점 중 문제가 발생했습니다.');
  }
};
