import { useEffect, useState } from 'react';
import ProblemContentText from '@/components/problem/problemContentText';
import ProblemHeader from '@/components/problem/problemHeader';
import ProblemOptionList from '@/components/problem/problemOptionList';
import PrimaryButton from '@/components/common/buttons/primaryButton';
import dummyProblems from '@/mock/dummyProblems.json';
import { Problem } from '@/types/ProblemTypes';
import { useRouter } from 'next/router';
import ProblemContentImage from '@/components/problem/problemContentImage';
import Swal from 'sweetalert';
import ProblemMemoIcon from '@/components/problem/problemMemoIcon';
import SecondaryButton from '@/components/common/buttons/secondaryButton';
import styles from './testProblems.module.css';

interface ProblemAnswer {
  problemId: number;
  userAnswer: number; // 사용자의 선택
  problemAnswer: number; // 문제의 정답
  solvedTime?: number; // 풀이 시간 (선택적)
}

interface Memo {
  memoId: number;
  content: string;
}

export default function Test() {
  const PROBLEM_COUNT = 15; // 문제 개수 고정
  const [problems, setProblems] = useState<Problem[]>([]);
  const [currentProblemIndex, setCurrentProblemIndex] = useState<number>(0); // 현재 문제 인덱스
  const [answers, setAnswers] = useState<ProblemAnswer[]>([]);
  const [startTime, setStartTime] = useState<number>(Date.now()); // 문제 시작 시간
  const [, setTotalSolvedTime] = useState<number>(0);
  const router = useRouter();
  const currentProblem = problems[currentProblemIndex];
  const [isEditingMemo, setIsEditingMemo] = useState<boolean>(false); // 메모 작성 중 여부
  const [newMemoContent, setNewMemoContent] = useState<string>(''); // 새 메모 또는 수정할 메모의 내용을 저장하는 상태
  const [selectedMemoIndex, setSelectedMemoIndex] = useState<number | null>(
    null,
  ); // 선택된 메모의 인덱스

  // 기존에 있던 dummyMemo를 상태로 변경
  const [memoList, setMemoList] = useState<Memo[]>([
    { memoId: 1, content: 'This is the first memo content.' },
    { memoId: 2, content: 'This is the second memo content.' },
    { memoId: 3, content: 'This is the third memo content.' },
    { memoId: 4, content: 'This is the fourth memo content.' },
  ]);

  const updateAnswers = (
    problemIndex: number,
    updatedFields: Partial<ProblemAnswer>,
  ) => {
    setAnswers((prevAnswers) => {
      const updatedAnswers = prevAnswers.map((answer, index) =>
        index === problemIndex ? { ...answer, ...updatedFields } : answer,
      );
      return updatedAnswers;
    });
  };

  useEffect(() => {
    const fixedProblems = dummyProblems.slice(0, PROBLEM_COUNT);
    setProblems(fixedProblems);

    const initializeAnswers = () => {
      const initialAnswers = fixedProblems.map((problem) => ({
        problemId: problem.problemId,
        problemAnswer: Number(problem.solution),
        userAnswer: 0, // 기본값은 0
        solvedTime: 0, // 기본 풀이 시간은 0
      }));
      setAnswers(initialAnswers);
    };

    const savedAnswers = sessionStorage.getItem('savedAnswers');

    if (savedAnswers) {
      Swal({
        title: '이전에 저장된 답안을 불러올까요?',
        text: '이전에 진행한 답안이 있습니다. 불러올까요?',
        icon: 'info',
        buttons: ['새로 시작', '불러오기'],
        dangerMode: true,
      }).then((willContinue) => {
        if (willContinue) {
          const parsedAnswers = JSON.parse(savedAnswers);
          setAnswers(parsedAnswers);
        } else {
          initializeAnswers();
        }
      });
    }
    initializeAnswers();
  }, []);

  useEffect(() => {
    const start = Date.now();
    setStartTime(start);

    return () => {
      const timeSpent = Math.floor((Date.now() - start) / 1000);
      setTotalSolvedTime(
        (prevTotalSolvedTime) => prevTotalSolvedTime + timeSpent,
      );

      updateAnswers(currentProblemIndex, {
        solvedTime: (answers[currentProblemIndex]?.solvedTime || 0) + timeSpent,
      });
    };
  }, [currentProblemIndex]);

  const progressPercentage =
    problems.length > 1
      ? Math.floor((currentProblemIndex / problems.length) * 100)
      : 100; // 만약 문제가 1개라면 무조건 100%로 설정

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
    updateAnswers(problemIndex, { userAnswer });
  };

  const handleSubmit = () => {
    const timeSpent = Math.floor((Date.now() - startTime) / 1000);

    updateAnswers(currentProblemIndex, {
      solvedTime: (answers[currentProblemIndex]?.solvedTime || 0) + timeSpent,
    });

    sessionStorage.removeItem('savedAnswers');
    router.push('/test/result');
  };

  const handleMemoClick = (index: number) => {
    const selectedMemo = memoList[index];
    setSelectedMemoIndex(index);
    setNewMemoContent(selectedMemo.content); // 기존 메모 내용을 입력란에 표시
    setIsEditingMemo(true); // 수정 모드로 전환
  };

  const handleMemoSave = () => {
    // 메모 저장 로직 (수정 및 새 메모 추가)
    if (selectedMemoIndex !== null) {
      const updatedMemoList = [...memoList];
      updatedMemoList[selectedMemoIndex] = {
        ...updatedMemoList[selectedMemoIndex],
        content: newMemoContent,
      };
      setMemoList(updatedMemoList); // 수정된 메모 업데이트
    } else {
      const newMemo = {
        memoId: memoList.length + 1,
        content: newMemoContent,
      };
      setMemoList([...memoList, newMemo]); // 새 메모 추가
    }
    setIsEditingMemo(false); // 메모 저장 후 작성 창 종료
    setNewMemoContent(''); // 입력란 초기화
    setSelectedMemoIndex(null); // 선택된 메모 초기화
  };

  const handleMemoCancel = () => {
    setIsEditingMemo(false);
    setNewMemoContent(''); // 취소 시 입력란 초기화
    setSelectedMemoIndex(null); // 선택된 메모 초기화
  };

  const handleNewMemo = () => {
    setIsEditingMemo(true);
    setSelectedMemoIndex(null); // 새 메모 작성을 위해 선택된 메모 초기화
    setNewMemoContent(''); // 새 메모 작성할 때는 내용 초기화
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
                className={`${styles['problem-solved-button']} ${
                  answer.userAnswer !== 0 ? styles.answered : styles.unanswered
                } ${index === currentProblemIndex && styles['problem-solved-button-active']}`}
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
                {currentProblem?.problemType?.problemTypeDetailCode === '0' && (
                  <ProblemContentImage
                    imageUrl={currentProblem?.content}
                    altText={currentProblem?.title || '문제 이미지'}
                  />
                )}
                {currentProblem?.problemType?.problemTypeDetailCode !== '0' && (
                  <ProblemContentText
                    problemContent={currentProblem?.content}
                  />
                )}
                <ProblemOptionList
                  selectedOption={answers[currentProblemIndex]?.userAnswer}
                  problemIndex={currentProblemIndex}
                  problemOptions={currentProblem?.problemOptions}
                  onTestProblemAnswer={handleAnswer}
                />
              </div>
            </div>
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
        )}

        <div className={styles['right-navigation']}>
          <h5 className={styles['problem-solved-title']}>나의 메모</h5>
          {isEditingMemo ? (
            <>
              <textarea
                className={styles['memo-textarea']}
                value={newMemoContent}
                onChange={(e) => setNewMemoContent(e.target.value)}
              />
              <div className={styles['memo-textarea-button-list']}>
                <SecondaryButton
                  label="저장"
                  size="small"
                  onClick={handleMemoSave}
                />
                <SecondaryButton
                  label="취소"
                  size="small"
                  onClick={handleMemoCancel}
                />
              </div>
            </>
          ) : (
            <div className={styles['memo-textarea-button-list']}>
              <SecondaryButton
                label="메모 작성"
                size="small"
                onClick={handleNewMemo}
              />
            </div>
          )}
          <div className={styles['memo-list']}>
            {memoList.map((memo, index) => (
              <ProblemMemoIcon
                key={memo.memoId}
                onClick={() => handleMemoClick(index)}
                role="button"
                tabIndex={0}
              />
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
