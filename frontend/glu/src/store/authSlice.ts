import { createSlice } from '@reduxjs/toolkit';
import { UserState } from '@/types/UserTypes';

const initialState: UserState = {
  isLoggedIn: false,
  userId: null,
  isFirst: false,
  nickname: null,
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    login: (state, action) => {
      return {
        ...state,
        isLoggedIn: true,
        userId: action.payload.userId,
        isFirst: action.payload.isFirst,
        nickname: action.payload.nickname,
      };
    },
    logout: (state) => {
      return {
        ...state,
        isLoggedIn: false,
        userId: null,
        isFirst: false,
        nickname: null,
      };
    },
  },
});

export const { login, logout } = authSlice.actions;
export default authSlice.reducer;
