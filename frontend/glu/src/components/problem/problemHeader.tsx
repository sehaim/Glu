/* eslint-disable jsx-a11y/no-static-element-interactions */
import { BsStar } from 'react-icons/bs';
import styles from './problemHeader.module.css';

interface ProblemHeaderProps {
  problemIndex?: number;
  problemLevel: string;
  problemType: string;
  problemTitle: string;
}

export default function ProblemHeader({
  problemIndex,
  problemLevel,
  problemType,
  problemTitle,
}: ProblemHeaderProps) {
  return (
    <div className={styles.container}>
      <div className={styles['status-container']}>
        <div className={styles['status-level-wrapper']}>
          <div className={styles['status-level']}>{problemLevel}</div>
        </div>
        <div className={styles['status-like-wrapper']}>
          <div className={styles['status-like-icon']}>
            <BsStar />
          </div>
        </div>
      </div>
      <div className={styles['info-container']}>
        <div className={styles['problem-type']}>
          <div className={styles['problem-type-text']}>{problemType}</div>
        </div>
        <div className={styles['problem-title']}>
          {problemIndex}
          {problemIndex && '.'} {problemTitle}
        </div>
      </div>
    </div>
  );
}
