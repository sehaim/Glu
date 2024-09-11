import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface AuthState {
  isLoggedIn: boolean;
  accessToken?: string;
}

const initialState: AuthState = {
  isLoggedIn: false,
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    login: (state, action: PayloadAction<string>) => {
      return {
        ...state,
        isLoggedIn: true,
        accessToken: action.payload,
      };
    },
    logout: (state) => {
      return {
        ...state,
        isLoggedIn: false,
        accessToken: undefined,
      };
    },
  },
});

export const { login, logout } = authSlice.actions;
export default authSlice.reducer;
