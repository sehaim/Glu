import { ProblemOption } from '@/types/ProblemTypes';
import { useEffect, useState } from 'react';
import styles from './problemOptionList.module.css';

interface ProblemOptionListProps {
  problemOptions: ProblemOption[];
}

export default function ProblemOptionList({
  problemOptions,
}: ProblemOptionListProps) {
  const [options, setOptions] = useState<ProblemOption[]>([]);

  useEffect(() => {
    setOptions(problemOptions);
  }, []);

  return (
    <div className={styles['problem-option-list']}>
      {options?.map((option) => (
        <div key={option.problemOptionId} className={styles['problem-option']}>
          <div className={styles['problem-option-number']}>
            {option.problemOptionId}
          </div>
          <div className={styles['problem-option-text']}>{option.option}</div>
        </div>
      ))}
    </div>
  );
}
