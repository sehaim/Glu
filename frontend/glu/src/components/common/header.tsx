import { useEffect } from 'react';
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
        <nav className={styles.nav_menu}>
          <ul>
            <li>
              <h1 className={styles.logo}>Glu</h1>
            </li>
            <li>종합 테스트</li>
            <li>유형별 테스트</li>
          </ul>
        </nav>
        <nav className={styles.info_menu}>
          <ul>
            <li>로그인</li>
            <li>회원가입</li>
          </ul>
        </nav>
      </div>
    </div>
  );
}
