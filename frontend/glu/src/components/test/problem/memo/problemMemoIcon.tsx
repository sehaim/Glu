import React from 'react';
import styles from './problemMemoIcon.module.css';

interface ProblemMemoIconProps {
  onClick: () => void;
  role?: string;
  tabIndex?: number;
}

export default function ProblemMemoIcon({
  onClick,
  role = 'button',
  tabIndex = 0,
}: ProblemMemoIconProps) {
  return (
    <button
      type="button"
      className={styles.memo}
      onClick={onClick}
      role={role}
      tabIndex={tabIndex}
      aria-label="Memo icon"
    />
  );
}
