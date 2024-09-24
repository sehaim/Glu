import { BACKEND_URL } from '@/config/config';
import axios, { AxiosInstance } from 'axios';
import { getCookie, setCookie } from 'cookies-next';

// 로그인이 필요없는 axios
export const defaultAxios: AxiosInstance = axios.create({
  baseURL: `${BACKEND_URL}`,
});

const accessToken = getCookie('accessToken');

// 로그인 후 axios
export const authAxios: AxiosInstance = axios.create({
  baseURL: `${BACKEND_URL}`,
  headers: {
    Authorization: `Bearer ${accessToken}` || '',
  },
});

// 토큰 재발급
export const refreshUserAPI = async (): Promise<string | null> => {
  try {
    const res = await defaultAxios.post(`auth/reissue`);
    const newToken: string = res.data.accessToken;
    return newToken;
  } catch (err) {
    console.log(err);
    alert('로그인 시간이 만료되었습니다. 다시 로그인해주세요.'); // 추후 수정 예정
    window.location.href = '/';
    return null;
  }
};
