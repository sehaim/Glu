import { SignupUser } from '@/types/UserTypes';
import { defaultAxios, sweetalertConfirm, sweetalertError } from '../common';

// 아이디 중복 확인
export const checkIdAPI = async (data: string): Promise<boolean> => {
  let isDuplicate: boolean = false;
  try {
    const res = await defaultAxios.get(`users/check?id=${data}`);
    isDuplicate = res.data;
  } catch {
    sweetalertError('아이디 중복 확인 오류', '입력값을 확인해주세요.');
  }
  return isDuplicate;
};

// 회원 가입
export const signupAPI = async (data: SignupUser) => {
  try {
    await defaultAxios.post(`users/register`, data);
    sweetalertConfirm('회원가입 완료', '회원가입이 완료되었습니다.');
    window.location.href = '/login';
  } catch {
    sweetalertError('회원가입 오류', '입력값을 확인해주세요.');
  }
};
