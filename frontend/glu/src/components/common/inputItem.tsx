import styles from './inputItem.module.css';

export default function InputItem({ label }) {
  return (
    <div className={styles['input-container']}>
      <div className={styles['input-item']}>
        <div className={styles['input-label']}>{label}</div>
        <input type="text" />
      </div>
    </div>
  );
}
