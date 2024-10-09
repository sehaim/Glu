import { FaCheck } from 'react-icons/fa';
import { LuDot } from 'react-icons/lu';
import styles from './problemNavigationManager.module.css';

interface ProblemAnswer {
  problemId: string;
  userAnswer: string;
  problemAnswer: string;
  solvedTime?: number;
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
      <p className={styles['container-title']}>해결한 문제</p>
      <ul className={styles['solved-button-list']}>
        {answers.map((answer, index) => (
          <li key={answer.problemId} className={styles['solved-list-item']}>
            <button
              type="button"
              className={`${styles['solved-button']} ${
                answer.userAnswer !== '' ? styles.answered : styles.unanswered
              } ${index === currentProblemIndex && styles['solved-button-active']}`}
              onClick={() => handleProblemIndexChange(index)}
              onKeyDown={(e) => {
                if (e.key === 'Enter' || e.key === ' ') {
                  handleProblemIndexChange(index);
                }
              }}
            >
              <p className={styles['solved-button-number']}>{index + 1}번</p>
              <p className={styles['solved-button-status']}>
                {answer.userAnswer !== '' ? (
                  <FaCheck className={styles.solve} />
                ) : (
                  <LuDot className={styles['solve-not']} />
                )}
              </p>
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
}
