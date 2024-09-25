import { LoginUser } from '@/types/UserTypes';
import { AxiosError } from 'axios';
import { setCookie, deleteCookie } from 'cookies-next';
import { createAuthAxios, defaultAxios } from '../common';

// 로그인
export const loginAPI = async (data: LoginUser) => {
  try {
    const res = await defaultAxios.post(`auth/login`, data);
    setCookie('accessToken', res.headers.accesstoken);
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
    const authAxios = createAuthAxios();
    await authAxios.post(`auth/logout`);
    deleteCookie('accessToken');
    window.location.href = '/';
  } catch {
    alert('로그아웃 실패'); // 추후 삭제 예정
  }
};
