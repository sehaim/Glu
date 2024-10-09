import { FaStar } from 'react-icons/fa6';
import { Problem, SolvedProblem } from '@/types/ProblemTypes';
import { postProblemLikeAPI, deleteProblemLikeAPI } from '@/utils/problem/like';
import { useState } from 'react';
import Link from 'next/link';
import styles from './testCardItem.module.css';

interface TestCardItemProps {
  problem: SolvedProblem | Problem;
}

export default function TestCardItem({ problem }: TestCardItemProps) {
  const [isFavorite, setIsFavorite] = useState(problem.isFavorite);

  const problemLevel = Number(problem.problemLevel.code.slice(-1));

  const handleLikeProblem = async () => {
    if (isFavorite) {
      deleteProblemLikeAPI(problem.problemId);
    } else {
      postProblemLikeAPI(problem.problemId);
    }
    setIsFavorite(!isFavorite);
  };

  const isSolvedProblem = (p: SolvedProblem | Problem): p is SolvedProblem => {
    return 'solveDate' in p;
  };

  return (
    <div
      className={styles['card-container']}
      style={{
        backgroundColor: `rgb(255, ${150 + (4 - problemLevel) * 18}, ${150 + (4 - problemLevel) * 18})`,
      }}
    >
      <div className={`${styles.element} ${styles.level}`}>
        LV.{problemLevel}
      </div>
      <FaStar
        size={22}
        className={`${styles.element} ${styles.star} ${isFavorite ? styles['star-active'] : ''}`}
        onClick={handleLikeProblem}
      />
      <Link
        href={`/problem/${problem.problemId}`}
        className={`${styles.element} ${styles.type}`}
      >
        {problem.problemTypeDetail.name}
      </Link>
      <div className={`${styles.element} ${styles.date}`}>
        {isSolvedProblem(problem) ? problem.solveDate.slice(0, 10) : ''}
      </div>
    </div>
  );
}
