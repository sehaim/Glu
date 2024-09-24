/* eslint-disable react/jsx-no-useless-fragment */
import { ReactNode, useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import { jwtDecode } from 'jwt-decode';
import { getCookie } from 'cookies-next';
import { login } from '@/store/authSlice';
import { useDispatch } from 'react-redux';
import MytestLayout from './mytest-layout';
import styles from './layout.module.css';
import Header from '../common/header';
import Footer from '../common/footer';

interface GlobalLayoutProps {
  children: ReactNode;
}

export default function GlobalLayout({ children }: GlobalLayoutProps) {
  const dispatch = useDispatch();
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userInfo, setUserInfo] = useState<{
    userId: number;
    nickname: string;
    isFirst: boolean;
  } | null>(null);

  useEffect(() => {
    const accessToken = getCookie('accessToken');

    if (accessToken) {
      const decodedToken: any = jwtDecode(accessToken);
      const { userId, nickname, isFirst } = decodedToken;

      setIsLoggedIn(true);
      setUserInfo({ userId, nickname, isFirst });

      if (userId && nickname && isFirst !== undefined) {
        dispatch(login({ userId, nickname, isFirst }));
      }
    }
  }, [dispatch]);

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
