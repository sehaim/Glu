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
        userId: action.payload.userId ?? state.userId,
        isFirst: action.payload.isFirst ?? state.isFirst,
        nickname: action.payload.nickname ?? state.nickname,
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
