import { SignupUser } from '@/types/UserTypes';
import { defaultAxios } from '../common';

// 아이디 중복 확인
export const checkIdAPI = async (data: string): Promise<boolean> => {
  let isDuplicate: boolean = false;
  try {
    const res = await defaultAxios.get(`users/check?id=${data}`);
    isDuplicate = res.data;
  } catch {
    alert('잘못된 접근입니다.'); // 추후 수정 예정 (에러페이지)
  }
  return isDuplicate;
};

// 회원 가입
export const signupAPI = async (data: SignupUser) => {
  try {
    await defaultAxios.post(`users/register`, data);
    alert('회원가입이 완료되었습니다.'); // 추후 수정
    window.location.href = '/login';
  } catch {
    alert('입력값을 확인해주세요'); // 추후 수정
  }
};
