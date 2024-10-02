/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  images: {
    domains: ['eglubucket.s3.ap-northeast-2.amazonaws.com'], // 여기에 사용하고자 하는 이미지 호스트 추가
  },
};

export default nextConfig;
