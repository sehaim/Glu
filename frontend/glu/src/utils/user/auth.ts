import { BACKEND_URL } from '@/config/config';
import axios, { AxiosInstance } from 'axios';

// access token 만료 확인 (클라이언트)
export const isTokenExpired = (token: string): boolean => {
  const decoded = JSON.parse(atob(token.split('.')[1]));
  return decoded.exp < Date.now() / 1000;
};

// 로그인

// 로그아웃
export const logout = () => {
  localStorage.removeItem('accessToken');
  window.location.href = '/login';
};
