import { BACKEND_URL } from '@/config/config';
import axios, { AxiosInstance } from 'axios';

const accessToken =
  typeof window !== 'undefined' ? localStorage.getItem('accessToken') : null;

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
export const refreshUserAPI = async () => {
  try {
    const res = await authAxios.post(`auth/reissue`);
    const newToken: string = res.data.accessToken;
    localStorage.setItem('accessToken', newToken);
  } catch {
    localStorage.removeItem('accessToken');
    alert('로그인 시간이 만료되었습니다. 다시 로그인해주세요.'); // 추후 수정 예정
    window.location.href = '/';
  }
};

// access 토큰 만료시 axios interceptor
authAxios.interceptors.response.use(
  (res) => res,
  (err) => {
    if (err.response.status === 401) refreshUserAPI();
  },
);

authAxios.defaults.withCredentials = true;
