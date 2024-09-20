import GlobalLayout from '@/components/layout/global-layout';
import '@/styles/global.css';
import '@/styles/color.css';
import type { AppProps } from 'next/app';
import { ReactNode } from 'react';
import { NextPage } from 'next';
// eslint-disable-next-line import/no-extraneous-dependencies
import { Provider } from 'react-redux';
import { store } from '@/store';
import Head from 'next/head';

type NextPageWithLayout = NextPage & {
  getLayout?: (page: ReactNode) => ReactNode;
};

export default function App({
  Component,
  pageProps,
}: AppProps & {
  Component: NextPageWithLayout;
}) {
  const getLayout = Component.getLayout ?? ((page: ReactNode) => page);

  return (
    <>
      <Head>
        <meta
          name="google-site-verification"
          content="V5ZYJCNdt_HzDPX05cwk1u-mp_gc1Oe1jmoD2mFXPko"
        />
        <meta name="description" content="글루 문해력 진단 및 향상 서비스" />
        <meta
          name="keywords"
          content="어린이 문해력, 문해력 진단, 어린이 독서 능력, 문해력 테스트, 어린이 독서 이해력, 글 읽기 능력, 독서 학습, 글루, Glu, 교육 플랫폼, 문해력 향상 프로그램, 학습 능력 평가, 독서 이해력 향상, 초등학생 문해력, 언어 능력 평가"
        />
        <meta name="author" content="ssafy" />
      </Head>
      <Provider store={store}>
        <GlobalLayout>{getLayout(<Component {...pageProps} />)}</GlobalLayout>
      </Provider>
    </>
  );
}
