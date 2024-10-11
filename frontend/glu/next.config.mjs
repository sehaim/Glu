/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  images: {
    domains: [
      'eglubucket.s3.ap-northeast-2.amazonaws.com', // 사용하고자 하는 이미지 호스트 추가
      'eglubucket.s3.amazonaws.com', // 다른 버킷 URL
    ],
  },
};

export default nextConfig;
