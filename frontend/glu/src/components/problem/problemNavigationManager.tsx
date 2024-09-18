import styles from './problemNavigationManager.module.css'; // 새로운 CSS 파일로 스타일을 분리

interface ProblemAnswer {
  problemId: number;
  userAnswer: string; // 사용자의 선택
  problemAnswer: string; // 문제의 정답
  solvedTime?: number; // 풀이 시간 (선택적)
}

interface ProblemSolvedNavigationProps {
  answers: ProblemAnswer[];
  currentProblemIndex: number;
  onProblemIndexChange: (index: number) => void;
}

export default function ProblemSolvedNavigation({
  answers,
  currentProblemIndex,
  onProblemIndexChange,
}: ProblemSolvedNavigationProps) {
  const handleProblemIndexChange = (index: number) => {
    onProblemIndexChange(index);
  };

  return (
    <div className={styles.container}>
      <h5 className={styles['container-title']}>해결한 문제</h5>
      <ul className={styles['solved-button-list']}>
        {answers.map((answer, index) => (
          <button
            key={answer.problemId}
            type="button"
            className={`${styles['solved-button']} ${
              answer.userAnswer !== '' ? styles.answered : styles.unanswered
            } ${
              index === currentProblemIndex && styles['solved-button-active']
            }`}
            onClick={() => handleProblemIndexChange(index)}
            onKeyDown={(e) => {
              if (e.key === 'Enter' || e.key === ' ') {
                handleProblemIndexChange(index);
              }
            }}
          >
            <p className={styles['solved-button-number']}>{index + 1}번</p>
            <p className={styles['solved-button-status']}>
              {answer.userAnswer !== '' ? '✅' : '❌'}
            </p>
          </button>
        ))}
      </ul>
    </div>
  );
}
