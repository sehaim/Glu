import styles from '../userRegist.module.css';

export default function Login() {
  return (
    <div className={styles.container}>
      <div className={styles.section}>
        <div className={styles.title}>로그인</div>
        <div className={styles['input-container']}>
          <div className={styles['input-item']}>
            <div className={styles['input-label']}>아이디</div>
            <input type="text" />
          </div>
        </div>
      </div>
    </div>
  );
}
