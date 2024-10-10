import { BACKEND_URL } from '@/config/config';
import axios, { AxiosInstance } from 'axios';
import { getCookie, setCookie, deleteCookie } from 'cookies-next';
import { IncomingMessage, ServerResponse } from 'http';
import Swal from 'sweetalert2';

export const sweetalertConfirm = (
  header: string,
  content: string,
  button?: string,
) => {
  return Swal.fire({
    title: header,
    text: content,
    icon: 'success',
    confirmButtonText: button || '확인',
    allowOutsideClick: false,
  });
};

export const sweetalertError = (
  header: string,
  content: string,
  button?: string,
) => {
  return Swal.fire({
    title: header,
    text: content,
    icon: 'error',
    confirmButtonColor: 'var(--RED)',
    confirmButtonText: button || '확인',
    allowOutsideClick: false,
  });
};

export const sweetalertLevelTest = () => {
  return Swal.fire({
    title: '레벨 테스트',
    html: '레벨 테스트 응시 후, <br> 종합 테스트를 추천받을 수 있습니다.',
    imageUrl: '/images/problem/pencil.png',
    imageWidth: 35,
    imageHeight: 35,
    imageAlt: 'Pencil icon',
    confirmButtonColor: 'var(--ORANGE)',
    confirmButtonText: '레벨테스트 보러 가기',
  });
};

// 로그인이 필요없는 axios
export const defaultAxios: AxiosInstance = axios.create({
  baseURL: `${BACKEND_URL}`,
  withCredentials: true, // 쿠키 전송 허용
});

// 토큰 재발급
export const refreshUserAPI = async (context?: {
  req: IncomingMessage;
  res: ServerResponse;
}): Promise<string | null> => {
  try {
    const res = await defaultAxios.post(`auth/reissue`);
    const newToken: string = res.headers.accesstoken;
    return newToken;
  } catch (err) {
    deleteCookie(
      'accessToken',
      context ? { req: context.req, res: context.res } : {},
    );

    sweetalertError(
      '로그인 만료',
      '로그인 시간이 만료되었습니다. 다시 로그인해주세요.',
    );

    return null;
  }
};

// 로그인 후 사용 가능한 axios 인스턴스 생성
export const createAuthAxios = (context?: {
  req: IncomingMessage;
  res: ServerResponse;
}): AxiosInstance => {
  const accessToken = getCookie(
    'accessToken',
    context
      ? {
          req: context.req,
          res: context.res,
        }
      : {},
  );

  const instance = axios.create({
    baseURL: `${BACKEND_URL}`,
    headers: {
      Authorization: `Bearer ${accessToken}`,
      'Content-Type': 'application/json',
    },
    withCredentials: true, // 쿠키 전송 허용
  });

  // Axios 인터셉터 설정
  instance.interceptors.response.use(
    (response) => response,
    async (error) => {
      if (error.response.httpStatus === 401) {
        const newToken = context
          ? await refreshUserAPI(context)
          : await refreshUserAPI();
        if (newToken) {
          // 새로운 토큰으로 헤더 업데이트
          const updatedConfig = {
            ...error.config,
            headers: {
              ...error.config.headers,
              Authorization: `Bearer ${newToken}`, // 새로운 토큰 설정
            },
          };

          setCookie(
            'accessToken',
            newToken,
            context
              ? {
                  req: context.req,
                  res: context.res,
                  maxAge: 60 * 60 * 24 * 14,
                  path: '/',
                }
              : {
                  maxAge: 60 * 60 * 24 * 14,
                  path: '/',
                },
          );

          return axios(updatedConfig); // 요청 재시도
        }
      }
      return Promise.reject(error);
    },
  );

  return instance;
};

export const hasEnglish = (data: string) => {
  return /[a-zA-Z]/.test(data);
};

export const hasKorean = (data: string) => {
  return /[가-힣ㄱ-ㅎㅏ-ㅣ]/.test(data);
};

export const hasNumber = (data: string) => {
  return /\d/.test(data);
};

export const hasSpecialChar = (data: string) => {
  return /[^a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ]/.test(data);
};
