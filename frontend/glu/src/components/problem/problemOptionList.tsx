import { ProblemOption } from '@/types/ProblemTypes';
import { useEffect, useState } from 'react';
import styles from './problemOptionList.module.css';

interface ProblemOptionListProps {
  curSelectedIndex: number;
  problemIndex?: number;
  problemOptions: ProblemOption[];
  onAnswer?: (problemIndex: number, problemAnswer: number) => void;
}

export default function ProblemOptionList({
  curSelectedIndex,
  problemIndex,
  problemOptions,
  onAnswer,
}: ProblemOptionListProps) {
  const [options, setOptions] = useState<ProblemOption[]>([]);

  useEffect(() => {
    setOptions(problemOptions);
  }, [problemOptions]);

  const handleOptionClick = (userAnswer: number) => {
    onAnswer(problemIndex, userAnswer);
  };

  const handleKeyDown = (event: React.KeyboardEvent, optionId: number) => {
    if (event.key === 'Enter' || event.key === ' ') {
      handleOptionClick(optionId); // Enter나 Space를 누르면 옵션 선택
    }
  };

  return (
    <div className={styles['problem-option-list']}>
      {options?.map((option, index) => (
        <div
          key={option.problemOptionId}
          className={`${styles['problem-option']} ${
            curSelectedIndex === index + 1
              ? styles['problem-option-selected']
              : ''
          }`}
          onClick={() => handleOptionClick(option.problemOptionId)}
          onKeyDown={(e) => handleKeyDown(e, option.problemOptionId)} // 키보드 이벤트
          tabIndex={0} // 키보드 포커스 받을 수 있게 설정
          role="button" // ARIA 역할 설정
        >
          <div className={styles['problem-option-number']}>{index + 1}</div>
          <div className={styles['problem-option-text']}>{option.option}</div>
        </div>
      ))}
    </div>
  );
}
