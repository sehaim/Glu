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
      </Head>
      <Provider store={store}>
        <GlobalLayout>{getLayout(<Component {...pageProps} />)}</GlobalLayout>
      </Provider>
    </>
  );
}
