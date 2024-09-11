import styles from './mytest.module.css';
import MytestGrade from '@/components/mytest/mytestGrade';
import MytestAttendance from '@/components/mytest/mytestAttendance';

export default function MytestGrowth() {
  return (
    <div className={styles.section} id={styles.row}>
      <MytestGrade />
      <MytestAttendance />
    </div>
  );
}
