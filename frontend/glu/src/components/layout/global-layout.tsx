/* eslint-disable react/jsx-no-useless-fragment */
import { ReactNode } from 'react';
import { useRouter } from 'next/router';
import MytestLayout from './mytest-layout';
import styles from './layout.module.css';
import Header from '../common/header';
import Footer from '../common/footer';

export default function GlobalLayout({ children }: { children: ReactNode }) {
  const router = useRouter();

  const isMytestRoute = router.pathname.startsWith('/mytest');

  return (
    <div className={styles.container}>
      <Header color="white" />
      <main className={styles.main}>
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
