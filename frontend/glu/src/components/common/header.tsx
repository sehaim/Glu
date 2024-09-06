import { useEffect } from 'react';
import Link from 'next/link';
import styles from './header.module.css';

const getHeaderStyle = (color: string) => {
  const style: { [key: string]: string } = {};

  if (color === 'white') {
    style['--background-color'] = 'var(--WHITE)';
  } else if (color === 'transparent') {
    style['--background-color'] = 'transparent';
  }

  return style;
};

export default function Header({ color }: { color: string }) {
  const headerStyle = getHeaderStyle(color);

  useEffect(() => {
    // eslint-disable-next-line no-console
    console.log('Header component rendered with color:', color);
  }, [color]);

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
