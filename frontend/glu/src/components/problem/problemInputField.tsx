import { useEffect, useState } from 'react';
import styles from './problemInputField.module.css';

interface ProblemEssayProps {
  initialAnswer?: string;
  problemId?: string;
  placeholder: string;
  onTestProblemAnswer?: (problemId: string, problemAnswer: string) => void; // 테스트 문제에 대한 콜백
  onSingleProblemAnswer?: (problemAnswer: string) => void; // 단일 문제에 대한 콜백
}

export default function ProblemEssay({
  initialAnswer = '',
  problemId,
  placeholder,
  onTestProblemAnswer,
  onSingleProblemAnswer,
}: ProblemEssayProps) {
  const [userInput, setUserInput] = useState<string>(initialAnswer);

  useEffect(() => {
    setUserInput(initialAnswer);
  }, [initialAnswer]);

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const userAnswer = event.target.value;
    setUserInput(event.target.value);

    // Test 문제 풀이
    if (onTestProblemAnswer && typeof problemId !== 'undefined') {
      onTestProblemAnswer(problemId, userAnswer);
    }

    // 단일 문제 풀이
    if (onSingleProblemAnswer) {
      onSingleProblemAnswer(userAnswer);
    }
  };

  return (
    <div className={styles['problem-input-field']}>
      <input
        id="essay-answer"
        value={userInput}
        onChange={handleInputChange}
        className={styles['problem-input']}
        placeholder={placeholder}
      />
    </div>
  );
}
