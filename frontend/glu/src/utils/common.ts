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

// 토큰 재발급
export const refreshAPI = async () => {
  await authAxios
    .post(`${BACKEND_URL}/auth/reissue`)
    .then((res) => {
      localStorage.setItem('accessToken', res.data.accessToken);
    })
    .catch(() => {
      localStorage.removeItem('accessToken');
    });
};

// access 토큰 만료시 axios interceptor
authAxios.interceptors.response.use(
  (res) => res,
  (err) => {
    if (err.response.status === 401) refreshAPI();
  },
);

authAxios.defaults.withCredentials = true;
