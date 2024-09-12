import styles from './mytestAttendance.module.css';

// eslint-disable-next-line import/no-anonymous-default-export, react/display-name
export default function () {
  return (
    <div className={styles.container}>
      <div className={styles['container-header']}>
        <div className={styles['container-name']}>출석율</div>
      </div>
      <div className={styles['attendance-calendar']}>달력</div>
    </div>
  );
}
