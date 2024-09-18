import { authAxios } from '../common';

// 메모 가져오기
export const getProblemMemo = async (
  problemId: number,
  page: number,
  size: number,
  sort: string,
) => {
  try {
    const res = await authAxios.get(`/problems/${problemId}/memo`, {
      params: {
        page,
        size,
        sort,
      },
    });

    // 커스텀 응답에서 httpStatus 확인
    if (res.data.httpStatus === 400) {
      throw new Error(res.data.detailMessage || '잘못된 요청입니다.');
    }

    return res.data; // 응답 데이터를 반환
  } catch (error) {
    throw new Error('메모를 가져오는 중 문제가 발생했습니다.');
  }
};

// 메모 등록
export const postProblemMemo = async (problemId: number, content: string) => {
  try {
    const res = await authAxios.post(`/problems/${problemId}/memo`, {
      content,
    });

    // 커스텀 응답에서 httpStatus 확인
    if (res.data.httpStatus === 400) {
      throw new Error(res.data.detailMessage || '잘못된 요청입니다.');
    }

    return res.data; // 응답 데이터를 반환
  } catch (error) {
    throw new Error('메모를 등록하는 중 문제가 발생했습니다.');
  }
};

// 메모 수정
export const putProblemMemo = async (
  problemId: number,
  memoIndex: number,
  content: string,
) => {
  try {
    const res = await authAxios.put(`/problems/${problemId}/memo`, {
      memoIndex,
      content,
    });

    // 커스텀 응답에서 httpStatus 확인
    if (res.data.httpStatus === 400) {
      throw new Error(res.data.detailMessage || '잘못된 요청입니다.');
    }

    return res.data; // 응답 데이터를 반환
  } catch (error) {
    throw new Error('메모를 수정하는 중 문제가 발생했습니다.');
  }
};

// 메모 삭제
export const deleteProblemMemo = async (
  problemId: number,
  memoIndex: number,
) => {
  try {
    const res = await authAxios.delete(`/problems/${problemId}/memo`, {
      params: {
        memoIndex,
      },
    });

    // 커스텀 응답에서 httpStatus 확인
    if (res.data.httpStatus === 400) {
      throw new Error(res.data.detailMessage || '잘못된 요청입니다.');
    }

    return res.data; // 응답 데이터를 반환
  } catch (error) {
    throw new Error('메모를 삭제하는 중 문제가 발생했습니다.');
  }
};
