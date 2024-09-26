import { useEffect, useMemo, useState } from 'react';
import ProblemContentText from '@/components/problem/problemContentText';
import ProblemHeader from '@/components/problem/problemHeader';
import ProblemOptionList from '@/components/problem/problemOptionList';
import PrimaryButton from '@/components/common/buttons/primaryButton';
import { Problem } from '@/types/ProblemTypes';
import { useRouter } from 'next/router';
import ProblemProgressBar from '@/components/problem/problemProgressBar';
import ProblemMemoManager from '@/components/problem/problemMemoManager';
import { Memo } from '@/types/MemoTypes';
import ProblemSolvedNavigation from '@/components/problem/problemNavigationManager';
import { postProblemMemoAPI, putProblemMemoAPI } from '@/utils/problem/memo';
// eslint-disable-next-line import/no-extraneous-dependencies
import throttle from 'lodash/throttle';
import ProblemImageOptionList from '@/components/problem/problemImageOptionList';
import { useDispatch } from 'react-redux';
import { levelUp } from '@/store/levelupSlice';
import styles from './testProblems.module.css';

export async function getServerSideProps() {
  const dummyProblems = await import('@/mock/dummyProblems.json');
  const dummyMemo = await import('@/mock/dummyMemo.json');

  return {
    props: {
      initialProblems: dummyProblems.default,
      initialMemoList: dummyMemo.default,
    },
  };
}

interface TestProps {
  initialProblems: Problem[];
  initialMemoList: Memo[];
}
interface ProblemAnswer {
  problemId: number;
  userAnswer: string; // 사용자의 선택
  problemAnswer: string; // 문제의 정답
  solvedTime?: number; // 풀이 시간 (선택적)
}

export default function Test({ initialProblems, initialMemoList }: TestProps) {
  const router = useRouter();
  const dispatch = useDispatch();
  const PROBLEM_COUNT = 15; // 문제 개수 고정
  const [problems] = useState<Problem[]>(
    initialProblems?.slice(0, PROBLEM_COUNT) || [],
  );
  const [currentProblemIndex, setCurrentProblemIndex] = useState<number>(0); // 현재 문제 인덱스
  const [answers, setAnswers] = useState<ProblemAnswer[]>([]);
  // 푼 문제 개수 계산
  const solvedCount = useMemo(() => {
    return answers.filter((answer) => answer.userAnswer !== '').length;
  }, [answers]);
  const [startTime, setStartTime] = useState<number>(Date.now()); // 문제 시작 시간
  const [, setTotalSolvedTime] = useState<number>(0);
  const currentProblem = problems[currentProblemIndex];
  const [memoList, setMemoList] = useState<Memo[]>(initialMemoList || []);
  const [isMobile, setIsMobile] = useState(false); // 초기값 false로 설정
  const [loading] = useState(false);

  useEffect(() => {
    const handleResize = throttle(() => {
      setIsMobile(window.innerWidth < 1024);
    }, 300); // 0.3초 간격으로 이벤트 처리

    if (typeof window !== 'undefined') {
      window.addEventListener('resize', handleResize);
      handleResize(); // 초기 사이즈 체크

      return () => {
        window.removeEventListener('resize', handleResize);
      };
    }

    return () => {};
  }, []);

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
    const initializeAnswers = () => {
      const initialAnswers = problems.map((problem) => ({
        problemId: problem.problemId,
        problemAnswer: problem.solution,
        userAnswer: '', // 기본값은 0
        solvedTime: 0, // 기본 풀이 시간은 0
      }));
      setAnswers(initialAnswers);
    };

    initializeAnswers();
  }, [problems]);

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

  // 퍼센티지 계산
  const progressPercentage = useMemo(() => {
    return problems.length > 0
      ? Math.floor((solvedCount / problems.length) * 100)
      : 100;
  }, [solvedCount, problems.length]);

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

  const handleAnswer = (problemIndex: number, userAnswer: string) => {
    updateAnswers(problemIndex, { userAnswer });
  };

  const handleSubmit = () => {
    const timeSpent = Math.floor((Date.now() - startTime) / 1000);

    updateAnswers(currentProblemIndex, {
      solvedTime: (answers[currentProblemIndex]?.solvedTime || 0) + timeSpent,
    });

    dispatch(
      levelUp({ level: 2, levelImage: '/images/glu_character_shadow.png' }),
    ); // 임시로 levelup상태로 만듦

    router.push('/test/result');
  };

  const handleMemoSave = async (newMemo: Memo) => {
    try {
      if (currentProblem && newMemo.memoId && newMemo.memoId !== -1) {
        // currentProblem이 존재하고, memoId가 존재하고, -1이 아닐 경우 => 기존 메모 수정
        await putProblemMemoAPI(
          currentProblem.problemId,
          newMemo.memoId,
          newMemo.content,
        );
        // TODO: 새로운 메모 받아오기
        // setMemoList((prevMemoList) => {
        //   const memoIndex = prevMemoList.findIndex(
        //     (memo) => memo.memoId === newMemo.memoId,
        //   );
        //   if (memoIndex > -1) {
        //     // 기존 메모 업데이트
        //     const updatedMemoList = [...prevMemoList];
        //     updatedMemoList[memoIndex] = newMemo;
        //     return updatedMemoList;
        //   }
        //   return prevMemoList;
        // });
      } else {
        // 새로운 메모 등록
        const createdMemo = await postProblemMemoAPI(
          currentProblem.problemId,
          newMemo.content,
        );
        // TODO: 이걸로는 안됨. 다시 메모를 받아와야함 -1로 보냈기 때문에
        setMemoList((prevMemoList) => [...prevMemoList, createdMemo]);
      }
    } catch (error) {
      console.error('메모 저장 중 오류 발생:', error);
    }
  };

  if (loading) {
    return <div>결과 로딩 중...</div>; // 로딩 중일 때 표시할 메시지
  }

  return (
    <div className={styles.container}>
      <div className={styles['problem-container']}>
        <div
          className={styles['problem-navigation']}
          style={{
            position: isMobile ? 'static' : 'sticky',
            top: '60px',
            zIndex: 10,
          }}
        >
          <ProblemSolvedNavigation
            answers={answers}
            currentProblemIndex={currentProblemIndex}
            onProblemIndexChange={handlProblemIndex}
          />
        </div>

        {currentProblem && (
          <div className={styles.problem}>
            <ProblemHeader
              problemId={currentProblem.problemId}
              problemIndex={currentProblemIndex + 1}
              problemLevel={currentProblem?.problemLevel?.name}
              problemType={currentProblem?.problemType?.name}
              problemTitle={currentProblem?.title}
              problemLike={false}
            />
            <div className={styles['problem-content']}>
              <ProblemContentText problemContent={currentProblem?.content} />
              {currentProblem?.problemType?.problemTypeDetailCode === '0' && (
                <ProblemImageOptionList
                  selectedOption={answers[currentProblemIndex]?.userAnswer}
                  problemIndex={currentProblemIndex}
                  problemOptions={currentProblem?.problemOptions}
                  onTestProblemAnswer={handleAnswer}
                />
              )}
              {currentProblem?.problemType?.problemTypeDetailCode !== '0' && (
                <ProblemOptionList
                  selectedOption={answers[currentProblemIndex]?.userAnswer}
                  problemIndex={currentProblemIndex}
                  problemOptions={currentProblem?.problemOptions}
                  onTestProblemAnswer={handleAnswer}
                />
              )}
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
            <ProblemProgressBar progressPercentage={progressPercentage} />
          </div>
        )}

        <div
          className={styles['problem-memo']}
          style={{
            position: isMobile ? 'static' : 'sticky',
            top: '60px',
            zIndex: 10,
          }}
        >
          <ProblemMemoManager memoList={memoList} onSaveMemo={handleMemoSave} />
        </div>
      </div>
    </div>
  );
}
