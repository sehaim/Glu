/* eslint-disable react/button-has-type */
import styles from './primaryButton.module.css';

interface PrimaryButtonProps {
  label: string;
  size: 'small' | 'medium' | 'large';
  onClick: () => void;
}

export default function PrimaryButton({
  label,
  size,
  onClick,
}: PrimaryButtonProps) {
  return (
    <button
      className={`${styles['button-container']} ${styles[size]}`}
      onClick={onClick}
    >
      {label}
    </button>
  );
}
