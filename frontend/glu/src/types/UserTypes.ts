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

export interface UserState {
  isLoggedIn: boolean;
  userId: number | null;
  isFirst: boolean;
  nickname: string | null;
}

export interface MypageUser {
  id: string;
  nickname: string;
  birth: string;
  stage: number;
  exp: number;
  imageUrl: string;
  attendanceRate: number;
  dayCount: number;
  problemTypeList: {
    level: number;
    score: number;
    type: {
      code: string;
      name: string;
    };
  }[];
}

export interface Birth {
  year: number;
  month: number;
  day: number;
}

export interface Attendance {
  date: string;
  totalSolvedProblemCnt: number;
}

export type Attendances = Attendance[];
