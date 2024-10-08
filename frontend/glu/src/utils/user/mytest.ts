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
  sort?: string,
  context?: GetServerSidePropsContext,
) => {
  try {
    const authAxios = createAuthAxios(context);

    const params = new URLSearchParams();
    params.append('problemTypeCode', problemTypeCode);
    params.append('page', String(page));
    params.append('size', '6');

    if (status) {
      params.append('status', status);
    }
    if (hasMemo !== undefined) {
      params.append('hasMemo', String(hasMemo));
    }
    if (isFavorite !== undefined) {
      params.append('isFavorite', String(isFavorite));
    }
    if (sort !== undefined) {
      params.append('sort', String(sort));
    }

    const res = await authAxios.get(`problems/solve?${params.toString()}`);
    return res.data;
  } catch {
    return null;
  }
};
