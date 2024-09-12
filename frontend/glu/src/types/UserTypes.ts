export interface SignupUser {
  id: string;
  password: string;
  nickname: string;
  birth: string;
}

export interface LoginUser {
  id: string;
  password: string;
}

export interface MypageUser {
  id: string;
  nickname: string;
  level: 0;
  birth: string;
}
