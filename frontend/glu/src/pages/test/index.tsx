import { useEffect, useState } from 'react';
import ProblemContentText from '@/components/problem/problemContentText';
import ProblemHeader from '@/components/problem/problemHeader';
import ProblemOptionList from '@/components/problem/problemOptionList';
import PrimaryButton from '@/components/common/buttons/primaryButton';
import styles from './test.module.css';
import dummyProblems from '../../mock/dummyProblems.json';
import { Problem } from '../../types/ProblemTypes';

interface ProblemAnswer {
  problemId: number;
  userAnswer: number; // 사용자의 선택
  problemAnswer: number; // 문제의 정답
  solvedTime?: number; // 풀이 시간 (선택적)
}

export default function Test() {
  const PROBLEM_COUNT = 15; // 문제 개수 고정
  const [problems, setProblems] = useState<Problem[]>([]);
  const [currentProblemIndex, setCurrentProblemIndex] = useState<number>(0); // 현재 문제 인덱스
  const [answers, setAnswers] = useState<ProblemAnswer[]>([]);
  const [startTime, setStartTime] = useState<number>(0); // 문제 시작 시간
  const [, setTotalSolvedTime] = useState<number>(0);

  useEffect(() => {
    const fixedProblems = dummyProblems.slice(0, PROBLEM_COUNT);
    setProblems(fixedProblems);

    const initialAnswers = fixedProblems.map((problem) => ({
      problemId: problem.problemId,
      problemAnswer: Number(problem.solution),
      userAnswer: 0, // 기본값은 0
      solvedTime: 0, // 기본 풀이 시간은 0
    }));

    setAnswers(initialAnswers);
  }, []);

  useEffect(() => {
    const start = Date.now();
    setStartTime(start);

    return () => {
      const end = Date.now();
      const timeSpent = Math.floor((end - start) / 1000);

      setTotalSolvedTime(
        (prevTotalSolvedTime) => prevTotalSolvedTime + timeSpent,
      );

      setAnswers((prevAnswers) => {
        const updatedAnswers = [...prevAnswers];
        const currentAnswer = updatedAnswers[currentProblemIndex] || {};
        const previousSolvedTime = currentAnswer.solvedTime || 0;

        updatedAnswers[currentProblemIndex] = {
          ...currentAnswer,
          solvedTime: previousSolvedTime + timeSpent,
        };

        return updatedAnswers;
      });
    };
  }, [currentProblemIndex]);

  const progressPercentage =
    problems.length > 1
      ? Math.floor((currentProblemIndex / problems.length) * 100)
      : 100; // 만약 문제가 1개라면 무조건 100%로 설정

  const currentProblem = problems[currentProblemIndex];

  const handleNextProblem = () => {
    if (currentProblemIndex < problems.length - 1) {
      setCurrentProblemIndex((prevIndex) => prevIndex + 1);
    }
  };

  const handlePrevProblem = () => {
    if (currentProblemIndex > 0) {
      setCurrentProblemIndex((prevIndex) => prevIndex - 1);
    }
  };

  const handlProblemIndex = (index: number) => {
    setCurrentProblemIndex(index);
  };

  const handleAnswer = (problemIndex: number, userAnswer: number) => {
    setAnswers((prevAnswers) => {
      const updatedAnswers = [...prevAnswers];
      const updatedAnswer = {
        ...updatedAnswers[problemIndex],
        userAnswer,
      };
      updatedAnswers[problemIndex] = updatedAnswer;
      return updatedAnswers;
    });
  };

  const handleSubmit = () => {
    const end = Date.now();
    const timeSpent = Math.floor((end - startTime) / 1000);

    setAnswers((prevAnswers) => {
      const updatedAnswers = [...prevAnswers];
      const currentAnswer = updatedAnswers[currentProblemIndex] || {};
      const previousSolvedTime = currentAnswer.solvedTime || 0;

      updatedAnswers[currentProblemIndex] = {
        ...currentAnswer,
        solvedTime: previousSolvedTime + timeSpent,
      };

      return updatedAnswers;
    });

    console.log('Submitting answers', answers);
  };

  return (
    <div className={styles.container}>
      <div className={styles['problem-container']}>
        <div className={styles['left-navigation']}>
          <h5 className={styles['problem-solved-title']}>해결한 문제</h5>
          <ul className={styles['problem-solved-list']}>
            {answers.map((answer, index) => (
              <button
                key={answer.problemId} // 문제의 고유한 ID를 key로 사용
                type="button"
                className={`${styles['problem-solved-button']} ${answer.userAnswer !== 0 ? styles.answered : styles.unanswered}`}
                onClick={() => handlProblemIndex(index)}
                onKeyDown={(e) => {
                  if (e.key === 'Enter' || e.key === ' ') {
                    handlProblemIndex(index); // Enter나 Space 키로도 문제 이동 가능
                  }
                }}
              >
                <p className={styles['problem-solved-button-number']}>
                  {index + 1}번
                </p>
                <p className={styles['problem-solved-button-status']}>
                  {answer.userAnswer !== 0 ? '✅' : '❌'}
                </p>
              </button>
            ))}
          </ul>
        </div>

        {currentProblem && (
          <div className={styles['problem-wrapper']}>
            <div className={styles.problem} key={currentProblem.problemId}>
              <ProblemHeader
                problemIndex={currentProblemIndex + 1}
                problemLevel={currentProblem?.problemLevel?.name}
                problemType={currentProblem?.problemType?.name}
                problemTitle={currentProblem?.title}
              />
              <div className={styles['problem-content']}>
                <ProblemContentText problemContent={currentProblem?.content} />
                <ProblemOptionList
                  curSelectedIndex={answers[currentProblemIndex]?.userAnswer}
                  problemIndex={currentProblemIndex}
                  problemOptions={currentProblem?.problemOptions}
                  onAnswer={handleAnswer}
                />
              </div>
            </div>
            {/* 이전/다음 문제로 이동하는 버튼 */}
            <div className={styles['problem-button-list']}>
              {currentProblemIndex > 0 ? (
                <PrimaryButton
                  size="small"
                  label="이전 문제"
                  onClick={handlePrevProblem}
                />
              ) : (
                <div />
              )}
              {currentProblemIndex === problems.length - 1 ? (
                <PrimaryButton
                  size="small"
                  label="제출하기"
                  onClick={handleSubmit}
                />
              ) : (
                <PrimaryButton
                  size="small"
                  label="다음 문제"
                  onClick={handleNextProblem}
                />
              )}
            </div>
          </div>
        )}

        <div className={styles['right-navigation']} />
      </div>

      {/* 프로그레스바 */}
      <div className={styles['progressbar-container']}>
        <div className={styles.progressbar}>
          <img
            src="/images/glu_character.png"
            alt="character"
            className={styles['progress-character']}
            style={{ left: `calc(${progressPercentage}%` }}
          />
        </div>
        <img
          src="/images/problem/house.png"
          alt="house"
          className={styles['progress-house']}
        />
      </div>
    </div>
  );
}
