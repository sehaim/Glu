import { FaStar } from 'react-icons/fa6';
import { SolvedProblem } from '@/types/ProblemTypes';
import styles from './testCardItem.module.css';

interface TestCardItemProps {
  problem: SolvedProblem;
}

export default function TestCardItem({ problem }: TestCardItemProps) {
  return (
    <div className={styles['card-container']}>
      <div className={styles.element} id={styles.level}>
        LV.{problem.problemLevel.code}
      </div>
      <FaStar size={20} className={styles.element} id={styles.star} />
      <div className={styles.element} id={styles.type}>
        {problem.problemTypeDetail.name}
      </div>
      <div className={styles.element} id={styles.date}>
        2024-08-20
      </div>
    </div>
  );
}
