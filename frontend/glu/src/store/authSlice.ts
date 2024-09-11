import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { User } from '@/types/UserTypes';

const initUser: User = {
  userId: '',
  password: '',
  nickname: '',
  didLevelTest: false,
};

const authSlice = createSlice({
  name: 'authSlice',
  initialState: {
    user: initUser,
  },

  reducers: {},
});
