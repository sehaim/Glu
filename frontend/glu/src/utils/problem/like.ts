import { createAuthAxios } from '../common';

// 좋아요
export const getProblemLikeAPI = async (problemId: string) => {
  try {
    const authAxios = createAuthAxios();

    const res = await authAxios.get(`/problems/${problemId}/favorite`);

    // 커스텀 응답에서 httpStatus 확인
    if (res.data.httpStatus === 400) {
      throw new Error(res.data.detailMessage || '잘못된 요청입니다.');
    }

    return res;
  } catch (error) {
    throw new Error('좋아요 등록 중 문제가 발생했습니다.');
  }
};

// 좋아요
export const postProblemLikeAPI = async (problemId: string) => {
  try {
    const authAxios = createAuthAxios();

    const res = await authAxios.post(`/problems/${problemId}/favorite`);

    // 커스텀 응답에서 httpStatus 확인
    if (res.data.httpStatus === 400) {
      throw new Error(res.data.detailMessage || '잘못된 요청입니다.');
    }

    return res;
  } catch (error) {
    throw new Error('좋아요 등록 중 문제가 발생했습니다.');
  }
};

// 좋아요 취소
export const deleteProblemLikeAPI = async (problemId: string) => {
  try {
    const authAxios = createAuthAxios();

    const res = await authAxios.delete(`/problems/${problemId}/favorite`);

    // 커스텀 응답에서 httpStatus 확인
    if (res.data.httpStatus === 400) {
      throw new Error(res.data.detailMessage || '잘못된 요청입니다.');
    }

    return res;
  } catch (error) {
    throw new Error('좋아요 취소 중 문제가 발생했습니다.');
  }
};
