import Link from 'next/link';
import { useRouter } from 'next/router';
import styles from './mytestHeader.module.css';

export default function MytestHeader() {
  const router = useRouter();

  const tabs = [
    { path: '/mytest/growth', label: '나의 성장' },
    { path: '/mytest/starred', label: '찜한 문제' },
    { path: '/mytest/correct', label: '해결한 문제' },
    { path: '/mytest/incorrect', label: '틀린 문제' },
  ];

  return (
    <div className={styles.container}>
      {tabs.map((tab) => (
        <Link
          href={tab.path}
          key={tab.path}
          className={`${styles['tab-container']} ${
            router.pathname === tab.path ? styles.active : ''
          }`}
        >
          <div className={styles['page-name']}>{tab.label}</div>
        </Link>
      ))}
    </div>
  );
}
