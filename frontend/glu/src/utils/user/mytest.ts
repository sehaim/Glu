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
  context: GetServerSidePropsContext,
  page: number,
  size: number,
) => {
  try {
    const authAxios = createAuthAxios(context);
    const res = await authAxios.get(`tests?page=${page}&size=${size}`);
    return res.data;
  } catch {
    console.log('에러'); // 추후 콘솔 수정
    return null;
  }
};

// 유형별 테스트 기록 조회 API ->  수정 예정
export const getSolvedTestAPI = async (context: GetServerSidePropsContext) => {
  try {
    const authAxios = createAuthAxios(context);
    const res = await authAxios.get(`tests`);
    return res.data;
  } catch {
    console.log('에러'); // 추후 콘솔 수정
    return null;
  }
};
