import { useEffect, useState } from 'react';
import styles from './problemOptionList.module.css';

interface ProblemOptionListProps {
  selectedOption: string;
  problemId?: string;
  problemOptions: string[];
  onTestProblemAnswer?: (problemId: string, problemAnswer: string) => void; // 테스트 문제에 대한 콜백
  onSingleProblemAnswer?: (problemAnswer: string) => void; // 단일 문제에 대한 콜백
}

export default function ProblemOptionList({
  selectedOption,
  problemId,
  problemOptions,
  onTestProblemAnswer,
  onSingleProblemAnswer,
}: ProblemOptionListProps) {
  const [options, setOptions] = useState<string[]>(problemOptions);

  useEffect(() => {
    setOptions(problemOptions);
  }, [problemOptions]);

  const handleOptionClick = (userAnswer: number | null | undefined) => {
    if (!userAnswer) return; // Null 또는 undefined 체크

    // Test 문제 풀이
    if (onTestProblemAnswer && typeof problemId !== 'undefined') {
      onTestProblemAnswer(problemId, String(userAnswer));
    }

    // 단일 문제 풀이
    if (onSingleProblemAnswer) {
      onSingleProblemAnswer(String(userAnswer));
    }
  };

  const handleKeyDown = (
    event: React.KeyboardEvent,
    userAnswer: number | null | undefined,
  ) => {
    if (!userAnswer) return; // Null 또는 undefined 체크
    if (event.key === 'Enter' || event.key === ' ') {
      handleOptionClick(userAnswer); // Enter나 Space를 누르면 옵션 선택
    }
  };

  if (!options) return <div>로딩중</div>;

  return (
    <div className={styles['problem-option-list']}>
      {options.map((option, index) => (
        <div
          key={option}
          className={`${styles['problem-option']} ${
            selectedOption === String(index + 1)
              ? styles['problem-option-selected']
              : ''
          }`}
          onClick={() => handleOptionClick(index + 1)}
          onKeyDown={(e) => handleKeyDown(e, index + 1)} // 키보드 이벤트
          tabIndex={0} // 키보드 포커스 받을 수 있게 설정
          role="button" // ARIA 역할 설정
        >
          <div className={styles['problem-option-number']}>{index + 1}</div>
          <div className={styles['problem-option-text']}>{option}</div>
        </div>
      ))}
    </div>
  );
}
