import { FaStar } from 'react-icons/fa6';
import { SolvedProblem } from '@/types/ProblemTypes';
import { postProblemLikeAPI, deleteProblemLikeAPI } from '@/utils/problem/like';
import { useState } from 'react';
import styles from './testCardItem.module.css';

interface TestCardItemProps {
  problem: SolvedProblem;
}

export default function TestCardItem({ problem }: TestCardItemProps) {
  const [isFavorite, setIsFavorite] = useState(problem.isFavorite);

  const handleLikeProblem = async () => {
    if (isFavorite) {
      deleteProblemLikeAPI(problem.problemId);
    } else {
      postProblemLikeAPI(problem.problemId);
    }
    setIsFavorite(!isFavorite);
  };

  return (
    <div className={styles['card-container']}>
      <div className={`${styles.element} ${styles.level}`}>
        LV.{problem.problemLevel.code.slice(-2)}
      </div>
      <FaStar
        size={22}
        className={`${styles.element} ${styles.star} ${isFavorite ? styles['star-active'] : ''}`}
        onClick={handleLikeProblem}
      />
      <div className={`${styles.element} ${styles.type}`}>
        {problem.problemTypeDetail.name}
      </div>
      <div className={`${styles.element} ${styles.date}`}>
        {problem.solveDate.slice(0, 10)}
      </div>
    </div>
  );
}
