import { LoginUser } from '@/types/UserTypes';
import { AxiosError } from 'axios';
import { authAxios, defaultAxios } from '../common';

// Base64URL을 Base64로 변환하는 함수
const base64UrlToBase64 = (base64Url: string): string => {
  return (
    base64Url.replace(/-/g, '+').replace(/_/g, '/') +
    '='.repeat((4 - (base64Url.length % 4)) % 4)
  );
};

// access token 만료 확인 (클라이언트)
export const isTokenExpired = (token: string): boolean => {
  // JWT의 페이로드 부분(Base64URL)을 가져와 Base64로 변환한 후 디코딩
  const base64Url = token.split('.')[1];
  const base64 = base64UrlToBase64(base64Url);
  const decoded = JSON.parse(atob(base64));

  return decoded.exp < Date.now() / 1000;
};

// 로그인
export const login = async (data: LoginUser) => {
  try {
    const res = await defaultAxios.post(`auth/login`, data);
    localStorage.setItem('accessToken', res.headers.accesstoken);
    window.location.href = '/';
  } catch {
    // alert 추후 수정 예정
    alert('아이디/비밀번호를 확인해주세요');
  }
};

// 로그아웃
export const logout = async () => {
  await authAxios
    .post(`auth/logout`)
    .then(() => {
      localStorage.removeItem('accessToken');
      window.location.href = '/';
    })
    .catch(() => {
      console.log('로그아웃 실패'); // 추후 삭제 예정
    });
};
