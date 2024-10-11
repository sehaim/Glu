import styles from './loading.module.css';

interface LoadingProps {
  size?: 'extra-small' | 'small' | 'medium' | 'large' | 'extra-large'; // 사이즈
  showText?: boolean; // 로딩 텍스트 표시 여부
}

export default function Loading({
  size = 'medium',
  showText = false,
}: LoadingProps) {
  return (
    <div className={styles['loading-container']}>
      <div className={`${styles.spinner} ${styles[size]}`} />
      {showText && (
        <div className={`${styles['loading-text']} ${styles[`text-${size}`]}`}>
          로딩 중입니다...
        </div>
      )}
    </div>
  );
}
