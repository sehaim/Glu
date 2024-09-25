import { GetServerSidePropsContext } from 'next';
import { createAuthAxios } from '../common';

export const getUserInfoAPI = async (context: GetServerSidePropsContext) => {
  try {
    const authAxios = createAuthAxios(context);
    const res = await authAxios.get(`users`);
    return res.data;
  } catch (err) {
    return null;
  }
};

export const getAttendanceAPI = async (context: GetServerSidePropsContext) => {
  try {
    const authAxios = createAuthAxios(context);
    const res = await authAxios.get(`users/attendance`);
    return res.data;
  } catch (err) {
    return null;
  }
};
