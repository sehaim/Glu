import MytestHeader from '@/components/mytest/mytestHeader';
import styles from './layout.module.css';

export default function MytestLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div id="page-container">
      <div id="page-title">나의 학습</div>
      <MytestHeader />
      <div className={styles['mytest-content']}>{children}</div>
    </div>
  );
}
