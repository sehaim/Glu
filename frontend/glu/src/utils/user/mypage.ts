import { GetServerSidePropsContext } from 'next';
import { Birth } from '@/types/UserTypes';
import axios from 'axios';
import { setCookie } from 'cookies-next';
import { createAuthAxios, refreshUserAPI, sweetalertConfirm } from '../common';

// yyyy-mm-dd 형식의 생년월일을 Birth 객체로 변환하는 함수
export function parseDate(dateString: string) {
  const [year, month, day] = dateString.split('-'); // '-'로 문자열을 나눔

  const birth: Birth = {
    year: parseInt(year, 10), // 정수로 변환하여 year 값에 넣음
    month: parseInt(month, 10), // 정수로 변환하여 month 값에 넣음
    day: parseInt(day, 10), // 정수로 변환하여 day 값에 넣음
  };

  return birth;
}

// user 정보 조회 API
export const getUserInfoAPI = async (context: GetServerSidePropsContext) => {
  try {
    const authAxios = createAuthAxios(context);
    const res = await authAxios.get(`users`);
    return res.data;
  } catch (err) {
    return null;
  }
};

// user 정보 수정 API
export const putUserInfoAPI = async (
  nickname?: string,
  password?: string,
  newPassword?: string,
  birth?: string,
) => {
  try {
    const authAxios = createAuthAxios();

    const requestBody: { [key: string]: string | null } = {
      nickname: nickname || null,
      password: password || null,
      newPassword: newPassword || null,
      birth: birth || null,
    };

    await authAxios.put(`users`, requestBody);
    const newToken = await refreshUserAPI();

    setCookie('accessToken', newToken, {
      maxAge: 60 * 60 * 24 * 14,
      path: '/',
    });

    let changedOption: string = '';

    if (nickname) {
      changedOption = '닉네임';
    } else if (newPassword) {
      changedOption = '비밀번호';
    } else if (birth) {
      changedOption = '생년월일';
    }

    sweetalertConfirm(
      `${changedOption} 변경 완료`,
      `${changedOption}이 변경되었습니다.`,
    );
  } catch (err) {
    if (axios.isAxiosError(err)) {
      if (err.response) {
        if (err.response.status === 403) {
          alert(`Error: ${err.response.statusText}`); // 추후 수정
        }
      }
    }
  }
};
