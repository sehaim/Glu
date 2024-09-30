import { GetServerSidePropsContext } from 'next';
import { createAuthAxios } from '../common';

// 종합테스트 이력 조회 API
export const getSolvedComprehensiveTestAPI = async (
  context: GetServerSidePropsContext,
) => {
  try {
    const authAxios = createAuthAxios(context);
    const res = await authAxios.get(`tests`);
    return res.data;
  } catch {
    console.log('에러'); // 추후 콘솔 수정
  }
};

// 유형별 테스트 이력 조회 API ->  수정 예정
export const getSolvedTestAPI = async (context: GetServerSidePropsContext) => {
  try {
    const authAxios = createAuthAxios(context);
    const res = await authAxios.get(`tests`);
    return res.data;
  } catch {
    console.log('에러'); // 추후 콘솔 수정
  }
};
