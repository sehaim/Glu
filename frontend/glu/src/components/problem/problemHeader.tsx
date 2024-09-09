// eslint-disable-next-line import/no-extraneous-dependencies
import { BsStar } from 'react-icons/bs';
import styles from './problemHeader.module.css';

interface ProblemHeaderProps {
  problemLevel: string;
  problemType: string;
  problemTitle: string;
}

export default function ProblemHeader({
  problemLevel,
  problemType,
  problemTitle,
}: ProblemHeaderProps) {
  return (
    <div className={styles.container}>
      <div className={styles['status-container']}>
        <div className={styles['status-level']}>{problemLevel}</div>
        <div className={styles['status-like-wrapper']}>
          <div className={styles['status-like-icon']}>
            <BsStar />
          </div>
        </div>
      </div>
      <div className={styles['info-container']}>
        <div className={styles['problem-type']}>{problemType}</div>
        <div className={styles['problem-instruction']}>{problemTitle}</div>
      </div>
    </div>
  );
}
