import { LoginUser } from '@/types/UserTypes';
import { AxiosError } from 'axios';
import { setCookie, deleteCookie } from 'cookies-next';
import {
  createAuthAxios,
  defaultAxios,
  sweetalertConfirm,
  sweetalertError,
} from '../common';

// 로그인
export const loginAPI = async (data: LoginUser) => {
  try {
    const res = await defaultAxios.post(`auth/login`, data);
    setCookie('accessToken', res.headers.accesstoken, {
      maxAge: 60 * 60 * 24 * 14,
      path: '/',
    });
    window.location.href = '/home';
  } catch (err) {
    if (err instanceof AxiosError && err.response) {
      const msg =
        err.response.data?.message || '로그인 중 오류가 발생했습니다.';
      sweetalertError('로그인 오류', msg);
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
    return await sweetalertConfirm('로그아웃', '로그아웃이 완료되었습니다.');
  } catch {
    sweetalertError(
      '로그아웃 오류',
      '로그아웃 도중 오류가 발생했습니다. 다시 시도해주세요.',
    );
  }
};
