import { ProblemOption } from '@/types/ProblemTypes';
import { useEffect, useState } from 'react';
import styles from './problemOptionList.module.css';

interface ProblemOptionListProps {
  selectedOption: string;
  problemIndex?: number;
  problemOptions: ProblemOption[];
  onTestProblemAnswer?: (problemIndex: number, problemAnswer: string) => void; // 테스트 문제에 대한 콜백
  onSingleProblemAnswer?: (problemAnswer: string) => void; // 단일 문제에 대한 콜백
}

export default function ProblemOptionList({
  selectedOption,
  problemIndex,
  problemOptions,
  onTestProblemAnswer,
  onSingleProblemAnswer,
}: ProblemOptionListProps) {
  const [options, setOptions] = useState<ProblemOption[]>([]);

  useEffect(() => {
    setOptions(problemOptions);
  }, [problemOptions]);

  const handleOptionClick = (userAnswer: string) => {
    // Test 문제 풀이
    if (onTestProblemAnswer && typeof problemIndex !== 'undefined') {
      onTestProblemAnswer(problemIndex, userAnswer);
    }

    // 단일 문제 풀이
    if (onSingleProblemAnswer) {
      onSingleProblemAnswer(userAnswer);
    }
  };

  const handleKeyDown = (event: React.KeyboardEvent, userAnswer: string) => {
    if (event.key === 'Enter' || event.key === ' ') {
      handleOptionClick(userAnswer); // Enter나 Space를 누르면 옵션 선택
    }
  };

  return (
    <div className={styles['problem-option-list']}>
      {options?.map((option, index) => (
        <div
          key={option.problemOptionId}
          className={`${styles['problem-option']} ${
            selectedOption === option.option
              ? styles['problem-option-selected']
              : ''
          }`}
          onClick={() => handleOptionClick(option.option)}
          onKeyDown={(e) => handleKeyDown(e, option.option)} // 키보드 이벤트
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
