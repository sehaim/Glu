import { LoginUser } from '@/types/UserTypes';
import { AxiosError } from 'axios';
import { defaultAxios } from '../common';

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

// // 로그아웃
// export const logoutAPI = async () => {
//   await clientAuthAxios
//     .post(`auth/logout`)
//     .then(() => {
//       document.cookie =
//         'accessToken=; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT;';
//       window.location.href = '/';
//     })
//     .catch(() => {
//       console.log('로그아웃 실패'); // 추후 삭제 예정
//     });
// };
