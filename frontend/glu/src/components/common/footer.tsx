import styles from './footer.module.css';

export default function Footer() {
  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <div className={styles['content-line']}>
          <div className={styles['content-item']}>
            © 2024 Glu Inc. All rights reserved.
          </div>
        </div>
        <div className={styles['content-line']}>
          <div className={styles['content-item']}>
            서울특별시 강남구 테헤란로 212, 801호
          </div>
          <div className={styles['content-item']}>전화: 02-1234-5678</div>
          <div className={styles['content-item']}>이메일: support@Glu.com</div>
        </div>
        <div className={styles['content-line']}>
          <div className={styles['content-item']}>홈</div>
          <div className={styles['content-item']}>이용약관</div>
          <div className={styles['content-item']}>개인정보 처리방침</div>
        </div>
      </div>
    </div>
  );
}
