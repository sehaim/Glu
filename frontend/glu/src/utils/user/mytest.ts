import { GetServerSidePropsContext } from 'next';
import { createAuthAxios } from '../common';

// 출석 정보 조회 API
export const getAttendanceAPI = async (context: GetServerSidePropsContext) => {
  try {
    const authAxios = createAuthAxios(context);
    const res = await authAxios.get(`users/attendance`);
    return res.data;
  } catch (err) {
    return null;
  }
};

// 종합테스트 기록 조회 API
export const getSolvedComprehensiveTestAPI = async (
  page: number,
  size: number,
  context?: GetServerSidePropsContext | undefined,
) => {
  try {
    const authAxios = createAuthAxios(context);
    const res = await authAxios.get(
      `tests?page=${page}&size=${size}&sort=createdDate,desc`,
    );
    return res.data;
  } catch {
    console.log('에러'); // 추후 콘솔 수정
    return null;
  }
};

// 유형별 테스트 기록 조회 API ->  수정 예정
export const getSolvedTypeTestAPI = async (
  problemTypeCode: string,
  page: number,
  status?: string,
  hasMemo?: boolean,
  isFavorite?: boolean,
  context?: GetServerSidePropsContext,
) => {
  try {
    const authAxios = createAuthAxios(context);
    const res = await authAxios.get(
      `problems/solve?status=${status}&problemTypeCode=${problemTypeCode}&hasMemo=${hasMemo}&isFavorite=${isFavorite}&page=${page}&size=4`,
    );
    return res.data;
  } catch {
    console.log('에러'); // 추후 콘솔 수정
    return null;
  }
};
