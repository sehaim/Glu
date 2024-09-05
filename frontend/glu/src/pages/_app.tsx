import GlobalLayout from '@/components/layout/global-layout';
import '@/styles/globals.css';
import '@/styles/color.css';
import type { AppProps } from 'next/app';
import { ReactNode } from 'react';
import { NextPage } from 'next';
// eslint-disable-next-line import/no-extraneous-dependencies
import { Provider } from 'react-redux';
import { store } from '@/store';

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
    <Provider store={store}>
      <GlobalLayout>{getLayout(<Component {...pageProps} />)}</GlobalLayout>
    </Provider>
  );
}
