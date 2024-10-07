import { SolvedProblemType } from '@/types/ProblemTypes';
import { PreviousSolvedTestType } from '@/types/TestTypes';
import { AxiosError } from 'axios';
import { GetServerSidePropsContext } from 'next';
import { createAuthAxios } from '../common';

export const transformProblemType = (
  problemTypeList: PreviousSolvedTestType[],
): SolvedProblemType[] => {
  return problemTypeList.map((item) => ({
    correctCount: item.correctCount,
    problemType: {
      problemTypeCode: item.problemType.code,
      name: item.problemType.name,
    },
  }));
};

export const formatTime = (totalSeconds: number | null): string => {
  if (totalSeconds === null) {
    return '--분 --초'; // null일 경우 기본 메시지 반환
  }

  const minutes = Math.floor(totalSeconds / 60);
  const seconds = totalSeconds % 60;
  return `${minutes}분 ${seconds}초`;
};

// 이전 테스트 결과 조회
export const getPreviousTestAPI = async (
  context: GetServerSidePropsContext,
) => {
  try {
    const authAxios = createAuthAxios(context);
    const res = await authAxios.get(`/tests/previous`);

    // HTTP 상태 코드 확인
    if (res.status === 400) {
      throw new Error(res.data.detailMessage || '잘못된 요청입니다.');
    }

    return res.data; // 정상 응답 반환
  } catch (error) {
    if (error instanceof AxiosError) {
      if (error.response?.status === 404) {
        return null; // 404 상태일 경우 처리
      }
    }

    throw new Error('이전 테스트 결과를 가져오는 중 문제가 발생했습니다.');
  }
};
