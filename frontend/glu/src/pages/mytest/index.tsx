import styles from './mytest.module.css';
import MytestHeader from '@/components/mytest/mytestHeader';

export default function Mytest() {
  return (
    <div id="page-container">
      <div id="page-title">나의 학습</div>
      <MytestHeader />
      <div className={styles.section}>
        <div>내용</div>
      </div>
    </div>
  );
}
