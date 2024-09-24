/* eslint-disable react/jsx-no-useless-fragment */
import { ReactNode } from 'react';
import { useRouter } from 'next/router';
import { isTokenExpired } from '@/utils/user/auth';
import { useDispatch } from 'react-redux';
import { login, logout } from '@/store/authSlice';
import { refreshUserAPI } from '@/utils/common';
import { parseCookies } from 'nookies';
import MytestLayout from './mytest-layout';
import styles from './layout.module.css';
import Header from '../common/header';
import Footer from '../common/footer';

export async function getServerSideProps(context: any) {
  // 서버에서 쿠키 파싱
  const cookies = parseCookies(context);
  const accessToken = cookies.accessToken || null;

  let isLoggedIn = false;

  if (accessToken) {
    if (!isTokenExpired(accessToken)) {
      isLoggedIn = true;
    } else {
      await refreshUserAPI();
      isLoggedIn = true;
    }
  }

  return {
    props: {
      isLoggedIn,
    },
  };
}

interface GlobalLayoutProps {
  children: ReactNode;
  isLoggedIn: boolean;
}

export default function GlobalLayout({
  children,
  isLoggedIn,
}: GlobalLayoutProps) {
  const router = useRouter();
  const isMytestRoute = router.pathname.startsWith('/mytest');
  const dispatch = useDispatch();

  if (isLoggedIn) {
    dispatch(login());
  } else {
    dispatch(logout());
  }

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
