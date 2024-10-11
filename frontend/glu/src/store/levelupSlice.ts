import { createSlice } from '@reduxjs/toolkit';

interface LevelState {
  level: number;
  levelImage: string;
  isLeveledUp: boolean;
}

const initialState: LevelState = {
  level: 1,
  isLeveledUp: false,
  levelImage: '',
};

const levelupSlice = createSlice({
  name: 'levelup',
  initialState,
  reducers: {
    levelUp: (state, action) => {
      return {
        ...state,
        level: action.payload.level,
        levelImage: action.payload.levelImage,
        isLeveledUp: true,
      };
    },
    resetLevel: (state) => {
      return {
        ...state,
        isLeveledUp: false,
      };
    },
  },
});

export const { levelUp, resetLevel } = levelupSlice.actions;
export default levelupSlice.reducer;
