import { BACKEND_URL } from '@/config/config';
import axios, { AxiosInstance } from 'axios';

const accessToken =
  typeof window !== 'undefined' ? sessionStorage.getItem('accessToken') : null;

// 로그인이 필요없는 axios
export const defaultAxios: AxiosInstance = axios.create({
  baseURL: `${BACKEND_URL}`,
});

// 로그인 후 사용할 axios (-> 토큰이 필요한 경우)
export const authAxios: AxiosInstance = axios.create({
  baseURL: `${BACKEND_URL}`,
  headers: {
    Authorization: `Bearer ${accessToken}` || '',
  },
});

authAxios.defaults.withCredentials = true;
