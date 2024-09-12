export interface LoginUser {
  id: string;
  password: string;
}

export interface User {
  userId: string;
  password: string;
  nickname: string;
  didLevelTest: boolean;
}

export interface MypageUser {
  id: string;
  nickname: string;
  level: 0;
  birth: string;
}
