import { BACKEND_URL } from '@/config/config';
import axios, { AxiosInstance } from 'axios';
import { parseCookies, setCookie } from 'nookies';

// 클라이언트에서 쿠키를 파싱하는 함수
const getCookie = (name: string) => {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop()?.split(';').shift();
  return null;
};

// 로그인이 필요없는 axios
export const defaultAxios: AxiosInstance = axios.create({
  baseURL: `${BACKEND_URL}`,
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

export const createClientAuthAxios = () => {
  const accessToken =
    typeof window !== 'undefined' ? getCookie('accessToken') : null;

  const instance = axios.create({
    baseURL: `${BACKEND_URL}`,
    headers: {
      Authorization: accessToken ? `Bearer ${accessToken}` : '', // accessToken이 있으면 Authorization 헤더에 추가
    },
    withCredentials: true, // 쿠키 전송 허용
  });

  // Axios 인터셉터 설정 (401 에러 시 토큰 재발급 로직 추가)
  instance.interceptors.response.use(
    (response) => response,
    async (error) => {
      if (error.response.status === 401) {
        // refreshToken 로직 (필요시 구현)
        const newToken = await refreshUserAPI();
        if (newToken) {
          // 새로운 토큰으로 헤더 업데이트
          instance.defaults.headers.Authorization = `Bearer ${newToken}`;
          error.config.headers.Authorization = `Bearer ${newToken}`;
          document.cookie = `accessToken=${newToken}; path=/; expires=Fri, 31 Dec 9999 23:59:59 GMT; SameSite=Strict;`;
          return axios(error.config); // 요청 재시도
        }
      }
      return Promise.reject(error);
    },
  );

  return instance;
};

// // 로그인 후 사용할 axios (클라이언트)
// export const clientAuthAxios: AxiosInstance = axios.create({
//   baseURL: `${BACKEND_URL}`,
//   headers: {
//     Authorization: `Bearer ${accessToken}`,
//   },
// });

// SSR에서 사용 가능한 axios 인스턴스 생성 함수
export const createServerAuthAxios = (context: any): AxiosInstance => {
  const cookies = parseCookies(context); // 서버 측에서 쿠키 파싱
  const accessToken = cookies.accessToken || null;

  const instance = axios.create({
    baseURL: `${BACKEND_URL}`,
    headers: {
      Authorization: accessToken ? `Bearer ${accessToken}` : '', // accessToken이 있으면 Authorization 헤더에 추가
    },
    withCredentials: true, // 쿠키 전송 허용
  });

  // Axios 인터셉터 설정 (401 에러 시 토큰 재발급 로직 추가)
  instance.interceptors.response.use(
    (response) => response,
    async (error) => {
      if (error.response.status === 401) {
        // refreshToken 로직 (필요시 구현)
        const newToken = await refreshUserAPI();
        if (newToken) {
          // 새로운 토큰으로 헤더 업데이트
          instance.defaults.headers.Authorization = `Bearer ${newToken}`;
          error.config.headers.Authorization = `Bearer ${newToken}`;
          setCookie(null, 'accessToken', newToken);
          return axios(error.config); // 요청 재시도
        }
      }
      return Promise.reject(error);
    },
  );

  return instance;
};

// // 로그인 후 사용할 axios (서버)
// export const serverAuthAxios: AxiosInstance = axios.create({
//   baseURL: `${BACKEND_URL}`,
//   headers: {
//     Authorization: `Bearer ${accessToken}`,
//   },
// });

// // clientAuthAxios access token 만료시 axios interceptor
// clientAuthAxios.interceptors.response.use(
//   (res) => res,
//   (err) => {
//     if (err.response.status === 401) {
//       refreshUserAPI();
//     }
//   },
// );

// // serverAuthAxios access token 만료시 axios interceptor
// serverAuthAxios.interceptors.response.use(
//   (res) => res,
//   (err) => {
//     if (err.response.status === 401) {
//       refreshUserAPI();
//     }
//   },
// );

// clientAuthAxios.defaults.withCredentials = true;
// serverAuthAxios.defaults.withCredentials = true;
