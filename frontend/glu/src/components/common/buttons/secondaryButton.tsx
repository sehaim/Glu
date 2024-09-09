/* eslint-disable react/button-has-type */
import styles from './secondaryButton.module.css';

interface SecondaryButtonProps {
  label: string;
  size: 'small' | 'medium' | 'large';
  onClick: () => void;
}

export default function SecondaryButton({
  label,
  size,
  onClick,
}: SecondaryButtonProps) {
  return (
    <button className={`${styles.button} ${styles[size]}`} onClick={onClick}>
      {label}
    </button>
  );
}
