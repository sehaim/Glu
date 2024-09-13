/* eslint-disable prettier/prettier */
import { useEffect, useState, useRef } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '@/store';
import Link from 'next/link';
import { HiOutlineLogout } from 'react-icons/hi';
import { logoutAPI } from '@/utils/user/auth';
import { logout } from '../../store/authSlice';
import styles from './header.module.css';

function throttle(
  func: (event: Event) => void,
  delay: number,
): (event: Event) => void {
  let lastCall = 0;
  return function callback(event: Event) {
    const now = new Date().getTime();
    if (now - lastCall < delay) return undefined;
    lastCall = now;
    return func(event);
  };
}

function debounce(
  func: (event: Event) => void,
  delay: number,
): (event: Event) => void {
  let timeoutId: ReturnType<typeof setTimeout> | null = null; // 타임아웃 ID를 저장할 변수

  return function callback(event: Event) {
    if (timeoutId) clearTimeout(timeoutId); // 이전 타임아웃이 있다면 취소

    timeoutId = setTimeout(() => {
      func(event); // 지정된 시간 후에 함수 실행
    }, delay);
  };
}

const getHeaderStyle = (color: string, isScrolled: boolean) => {
  const style: { [key: string]: string } = {};

  if (color === 'transparent') {
    style.backgroundColor = 'transparent'; // 카멜케이스로 수정
  } else {
    style.backgroundColor = isScrolled
      ? 'rgba(255, 255, 255, 1)' // 스크롤 시 흰색
      : 'transparent'; // 스크롤되지 않았을 때 투명
  }

  style.transition = 'background-color 0.5s ease'; // transition도 backgroundColor로 적용

  return style;
};

export default function Header({ color }: { color: string }) {
  const [isScrolled, setIsScrolled] = useState(false);
  const prevScrollYRef = useRef(0); // 이전 스크롤 값을 useRef로 관리

  useEffect(() => {
    const handleScroll = throttle(() => {
      const currentScrollY = window.scrollY;

      if (currentScrollY >= prevScrollYRef.current || currentScrollY < 10) {
        setIsScrolled(true); // 스크롤이 아래로 내려갔을 때 흰색으로 설정
      } else {
        setIsScrolled(false); // 스크롤이 위로 올라가면 투명으로 설정
      }

      prevScrollYRef.current = currentScrollY; // useRef로 이전 스크롤 위치 업데이트
    }, 100); // 100ms 간격으로 스크롤 이벤트 처리

    const handleScrollEnd = debounce(() => {
      if (window.scrollY < 10) {
        setIsScrolled(false); // 스크롤이 멈췄을 때 상단이면 흰색 보장
      }
    }, 100); // 스크롤이 멈춘 후 100ms 후에 상태를 체크

    window.addEventListener('scroll', handleScroll);
    window.addEventListener('scroll', handleScrollEnd); // 스크롤 멈춤 이벤트 추가

    return () => {
      window.removeEventListener('scroll', handleScroll);
      window.removeEventListener('scroll', handleScrollEnd);
    };
  }, []);

  const headerStyle = getHeaderStyle(color, isScrolled);

  // 로그인 상태에 따른 헤더 변경 구현
  const { isLoggedIn } = useSelector((state: RootState) => state.auth);
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
                  <Link href="/test">
                    종합
                    <br />
                    테스트
                  </Link>
                </li>
                <li>
                  <Link href="/problem/1">
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
                  <Link href="/test" className={styles['menu-name']}>
                    종합 테스트
                  </Link>
                </li>
                <li>
                  <Link href="/problem/1" className={styles['menu-name']}>
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
                <Link href="/login" className={styles['menu-name']}>
                  로그인
                </Link>
              </li>
              <li>
                <Link href="/signup" className={styles['menu-name']}>
                  회원가입
                </Link>
              </li>
            </ul>
          ) : (
            <ul>
              <li>
                <Link href="/mytest/growth" className={styles['menu-name']}>
                  나의 학습
                </Link>
              </li>
              <li>
                <Link href="/mypage" className={styles['menu-name']}>
                  나의 정보
                </Link>
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
