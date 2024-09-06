import { useEffect, useState } from 'react';
import Link from 'next/link';
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

const getHeaderStyle = (color: string, isScrolled: boolean) => {
  const style: { [key: string]: string } = {};

  if (color === 'transparent') {
    style['--background-color'] = 'transparent'; // color가 transparent면 항상 투명
  } else {
    style['--background-color'] = isScrolled ? 'var(--WHITE)' : 'transparent'; // 스크롤 여부에 따라 색상 변경
  }

  return style;
};

export default function Header({ color }: { color: string }) {
  const [isScrolled, setIsScrolled] = useState(true);
  const [prevScrollY, setPrevScrollY] = useState(0); // 이전 스크롤 위치를 저장

  useEffect(() => {
    const handleScroll = throttle(() => {
      const currentScrollY = window.scrollY;

      if (currentScrollY >= prevScrollY || currentScrollY < 10) {
        setIsScrolled(true); // 스크롤이 아래로 내려갔을 때 흰색으로 설정
      } else {
        setIsScrolled(false); // 스크롤이 위로 올라가면 투명으로 설정
      }

      setPrevScrollY(currentScrollY); // 이전 스크롤 위치 업데이트
    }, 500); // 500ms 간격으로 스크롤 이벤트 처리

    window.addEventListener('scroll', handleScroll);

    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, [prevScrollY]);

  const headerStyle = getHeaderStyle(color, isScrolled);

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
            <li>
              종합
              <br />
              테스트
            </li>
            <li>
              유형
              <br />
              테스트
            </li>
          </ul>
        </nav>
        <nav className={styles['info-menu']}>
          <ul>
            <li>
              <Link href="/">
                <h1 className={styles.logo}>Glu</h1>
              </Link>
            </li>
            <li>종합 테스트</li>
            <li>유형별 테스트</li>
          </ul>
        </nav>
        <nav className={styles['user-menu']}>
          <ul>
            <li>
              <Link href="/login">로그인</Link>
            </li>
            <li>
              <Link href="/signup">회원가입</Link>
            </li>
          </ul>
        </nav>
      </div>
    </div>
  );
}
