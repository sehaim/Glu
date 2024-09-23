import MytestGrade from '@/components/mytest/mytestGrade';
import MytestAttendance from '@/components/mytest/mytestAttendance';
import styles from './mytest.module.css';

export default function MytestGrowthPage() {
  return (
    <div className={styles.section} id={styles.row}>
      <MytestGrade />
      <MytestAttendance />
    </div>
  );
}
