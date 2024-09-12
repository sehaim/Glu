/* eslint-disable react/jsx-no-useless-fragment */
import { ReactNode, useEffect } from 'react';
import { useRouter } from 'next/router';
import { isTokenExpired } from '@/utils/user/auth';
import { useDispatch } from 'react-redux';
import { login, logout } from '@/store/authSlice';
import MytestLayout from './mytest-layout';
import styles from './layout.module.css';
import Header from '../common/header';
import Footer from '../common/footer';

export default function GlobalLayout({ children }: { children: ReactNode }) {
  const router = useRouter();

  const isMytestRoute = router.pathname.startsWith('/mytest');

  const dispatch = useDispatch();

  useEffect(() => {
    const token: string | null = localStorage.getItem('accessToken');

    if (token) {
      if (isTokenExpired(token)) {
        dispatch(logout());
      } else {
        dispatch(login(token));
      }
    } else {
      dispatch(logout());
    }
  }, [dispatch]);

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
