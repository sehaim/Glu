import { useEffect, useState, useRef } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '@/store';
import Link from 'next/link';
import { HiOutlineLogout } from 'react-icons/hi';
import { logoutAPI } from '@/utils/user/auth';
import throttle from 'lodash/throttle';
import { useRouter } from 'next/router';
import { logout } from '../../store/authSlice';
import styles from './header.module.css';

const getHeaderStyle = (color: string, isScrolled: boolean) => {
  const style: { [key: string]: string } = {};

  if (color === 'transparent') {
    style.backgroundColor = 'transparent';
  } else {
    style.backgroundColor = isScrolled
      ? 'VAR(--WHITE)' // 스크롤 시 흰색
      : 'transparent'; // 스크롤되지 않았을 때 투명
  }

  style.transition = 'background-color 0.5s ease';

  return style;
};

export default function Header({ color }: { color: string }) {
  const [isScrolled, setIsScrolled] = useState(false);
  const prevScrollYRef = useRef(0); // 이전 스크롤 값을 useRef로 관리

  const router = useRouter();

  useEffect(() => {
    const handleScroll = throttle(() => {
      const currentScrollY = window.scrollY;

      if (currentScrollY >= prevScrollYRef.current) {
        setIsScrolled(true); // 스크롤이 아래로 내려갔을 때 흰색으로 설정
      } else {
        setIsScrolled(false); // 스크롤이 위로 올라가면 투명으로 설정
      }

      prevScrollYRef.current = currentScrollY; // useRef로 이전 스크롤 위치 업데이트
    }, 100); // 100ms 간격으로 스크롤 이벤트 처리

    window.addEventListener('scroll', handleScroll);

    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);

  const headerStyle = getHeaderStyle(color, isScrolled);

  // 로그인 상태에 따른 헤더 변경 구현
  const { isLoggedIn } = useSelector((state: RootState) => state.auth);
  const { nickname } = useSelector((state: RootState) => state.auth);
  const dispatch = useDispatch();

  const handleLogout = () => {
    logoutAPI();
    dispatch(logout());
  };

  return (
    <div className={styles.container} style={headerStyle}>
      <div className={styles.content}>
        <nav className={styles['info-menu-mobile']}>
          <ul>
            <li>
              <Link href="/">
                <h1 className={styles.logo}>Glu</h1>
              </Link>
            </li>
            {isLoggedIn && (
              <>
                <li>
                  <Link
                    href="/test"
                    className={`${styles['menu-name']} ${router.pathname === '/test' ? styles.active : ''}`}
                  >
                    종합
                    <br />
                    테스트
                  </Link>
                </li>
                <li>
                  <Link
                    href="/problem"
                    className={`${styles['menu-name']} ${router.pathname === '/problem' ? styles.active : ''}`}
                  >
                    유형
                    <br />
                    테스트
                  </Link>
                </li>
              </>
            )}
          </ul>
        </nav>
        <nav className={styles['info-menu']}>
          <ul>
            <li>
              <Link href="/">
                <h1 className={styles.logo}>Glu</h1>
              </Link>
            </li>

            {isLoggedIn && (
              <>
                <li>
                  <Link
                    href="/test"
                    className={`${styles['menu-name']} ${router.pathname === '/test' ? styles.active : ''}`}
                  >
                    종합 테스트
                  </Link>
                </li>
                <li>
                  <Link
                    href="/problem"
                    className={`${styles['menu-name']} ${router.pathname === '/problem' ? styles.active : ''}`}
                  >
                    유형 테스트
                  </Link>
                </li>
              </>
            )}
          </ul>
        </nav>
        <nav className={styles['user-menu']}>
          {!isLoggedIn ? (
            <ul>
              <li>
                <Link
                  href="/login"
                  className={`${styles['menu-name']} ${router.pathname === '/login' ? styles.active : ''}`}
                >
                  로그인
                </Link>
              </li>
              <li>
                <Link
                  href="/signup"
                  className={`${styles['menu-name']} ${router.pathname === '/signup' ? styles.active : ''}`}
                >
                  회원가입
                </Link>
              </li>
            </ul>
          ) : (
            <ul>
              <li>
                <Link
                  href="/mytest/growth"
                  className={`${styles['menu-name']} ${router.pathname.startsWith('/mytest') ? styles.active : ''}`}
                >
                  나의 학습
                </Link>
              </li>
              <li>
                <Link
                  href="/mypage"
                  className={`${styles['menu-name']} ${router.pathname === '/mypage' ? styles.active : ''}`}
                >
                  나의 정보
                </Link>
              </li>
              <li className={styles['user-info']}>
                <div className={styles['user-nickname']}>{nickname}</div>
                <div>님</div>
              </li>
              <li className={styles['logout-btn-container']}>
                <HiOutlineLogout
                  size={24}
                  onClick={handleLogout}
                  className={`${styles['menu-name']} ${styles['logout-btn']}`}
                />
              </li>
            </ul>
          )}
        </nav>
      </div>
    </div>
  );
}
