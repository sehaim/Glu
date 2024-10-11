/* eslint-disable react/button-has-type */
import styles from './secondaryButton.module.css';

interface SecondaryButtonProps {
  label: string;
  size: 'small' | 'medium' | 'large';
  onClick: () => void;
  disabled?: boolean;
}

export default function SecondaryButton({
  label,
  size,
  onClick,
  disabled = false,
}: SecondaryButtonProps) {
  return (
    <button
      className={`${styles.button} ${styles[size]} ${disabled ? styles.disabled : ''}`}
      onClick={onClick}
      disabled={disabled}
    >
      {label}
    </button>
  );
}
