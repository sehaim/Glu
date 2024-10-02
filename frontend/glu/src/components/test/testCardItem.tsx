import { FaStar } from 'react-icons/fa6';
import styles from './testCard.module.css';

export default function TestCardItem() {
  return (
    <div className={styles['card-container']}>
      <div className={styles.element} id={styles.level}>
        LV.1
      </div>
      <div className={styles.element} id={styles.no}>
        No.123
      </div>
      <FaStar size={20} className={styles.element} id={styles.star} />
      <div className={styles.element} id={styles.type}>
        단어와 문장 규칙
      </div>
      <div className={styles.element} id={styles.date}>
        2024-08-20
      </div>
    </div>
  );
}
