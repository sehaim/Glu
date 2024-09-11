/* eslint-disable react/jsx-no-useless-fragment */
import { ReactNode } from 'react';
import MytestLayout from '@/pages/mytest/layout';
import { useRouter } from 'next/router';
import style from './global-layout.module.css';
import Header from '../common/header';
import Footer from '../common/footer';

export default function GlobalLayout({ children }: { children: ReactNode }) {
  const router = useRouter();

  const isMytestRoute = router.pathname.startsWith('/mytest');

  return (
    <div className={style.container}>
      <Header color="white" />
      <main className={style.main}>
        {isMytestRoute ? (
          <MytestLayout>{children}</MytestLayout>
        ) : (
          <>{children}</>
        )}
      </main>
      <Footer />
    </div>
  );
}
