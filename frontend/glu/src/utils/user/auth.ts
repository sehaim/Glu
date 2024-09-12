import { LoginUser } from '@/types/UserTypes';
import { authAxios, defaultAxios } from '../common';
// access token 만료 확인 (클라이언트)
export const isTokenExpired = (token: string): boolean => {
  const decoded = JSON.parse(atob(token.split('.')[1]));
  return decoded.exp < Date.now() / 1000;
};

// 로그인
export const login = async (data: LoginUser) => {
  await defaultAxios
    .post(`auth/login`, data)
    .then((res) => {
      localStorage.setItem('accessToken', res.data.accessToken);
    })
    .catch((err) => {
      // 에러 코드 및 alert 추후 수정 예정
      if (err.response.status === 500) {
        alert('아이디를 확인해주세요');
      } else if (err.response.status === 501) {
        alert('비밀번호를 확인해주세요');
      }
    });
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
