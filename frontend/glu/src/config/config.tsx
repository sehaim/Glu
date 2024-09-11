import configFile from './config.json';

const serverEnv = process.env.NODE_ENV;
const config = configFile[serverEnv];

export const { BACKEND_URL } = config;
export const { REDIRECT_URL } = config;
