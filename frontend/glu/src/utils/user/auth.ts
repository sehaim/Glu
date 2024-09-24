import { LoginUser } from '@/types/UserTypes';
import { AxiosError } from 'axios';
import { deleteCookie } from 'cookies-next';
import { clientAuthAxios, defaultAxios } from '../common';

// 로그인
export const loginAPI = async (data: LoginUser) => {
  try {
    const res = await defaultAxios.post(`auth/login`, data);

    document.cookie = `accessToken=${res.headers.accesstoken}; path=/; expires=Fri, 31 Dec 9999 23:59:59 GMT; SameSite=Strict;`;
    window.location.href = '/home';
  } catch (err) {
    if (err instanceof AxiosError && err.response) {
      const msg =
        err.response.data?.message || '로그인 중 오류가 발생했습니다.';
      // alert 추후 수정 예정
      alert(msg);
    }
  }
};

// 로그아웃
export const logoutAPI = async () => {
  try {
    await clientAuthAxios.post(`auth/logout`);
    deleteCookie('accessToken');
    window.location.href = '/';
  } catch {
    alert('로그아웃 실패'); // 추후 삭제 예정
  }
};
