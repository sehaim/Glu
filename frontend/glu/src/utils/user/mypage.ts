import { authAxios } from '../common';

export const getUserInfoAPI = async () => {
  try {
    const res = await authAxios.get(`users`);
    console.log('응답 끝');
    console.log(`res: ${res?.data}`);
    return res.data;
  } catch (err) {
    console.log(err);
    return null;
  }
};

export const getAttendanceAPI = async () => {
  try {
    const res = await authAxios.get(`users/attendance`);
    return res.data;
  } catch (err) {
    console.log(err);
    return null;
  }
};
