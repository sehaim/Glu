import {
  PreviousSolvedProblemType,
  SolvedProblemType,
} from '@/types/ProblemTypes';

export const transformProblemType = (
  problemTypeList: PreviousSolvedProblemType[],
): SolvedProblemType[] => {
  return problemTypeList.map((item) => ({
    correctCount: item.correctCount,
    problemType: {
      problemTypeCode: item.problemTypeCode,
      name: item.name,
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
