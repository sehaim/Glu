import MytestHeader from '@/components/mytest/mytestHeader';
import styles from './mytest.module.css';

export default function MytestLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div id="page-container" className={styles.container}>
      <div id="page-title">나의 학습</div>
      <MytestHeader />
      <div className={styles.content}>{children}</div>
    </div>
  );
}
