import { useEffect, useMemo, useState } from 'react';
import ProblemContentText from '@/components/problem/problemContentText';
import ProblemHeader from '@/components/problem/problemHeader';
import ProblemOptionList from '@/components/problem/problemOptionList';
import PrimaryButton from '@/components/common/buttons/primaryButton';
import { Problem } from '@/types/ProblemTypes';
import { useRouter } from 'next/router';
import ProblemProgressBar from '@/components/problem/problemProgressBar';
import ProblemMemoManager from '@/components/problem/problemMemoManager';
import ProblemSolvedNavigation from '@/components/problem/problemNavigationManager';
import {
  getRecommendedTestProblemsAPI,
  postTestProblemGradingAPI,
} from '@/utils/problem/test';
// eslint-disable-next-line import/no-extraneous-dependencies
import throttle from 'lodash/throttle';
import { useDispatch } from 'react-redux';
import { levelUp } from '@/store/levelupSlice';
import { GetServerSideProps } from 'next';
import Loading from '@/components/common/loading';
import ProblemInputField from '@/components/problem/problemInputField';
import styles from './testProblems.module.css';

export const getServerSideProps: GetServerSideProps = async (context) => {
  const dummyProblems = await import('@/mock/dummyProblems.json');

  let testProblems;

  try {
    testProblems = await getRecommendedTestProblemsAPI(context);
  } catch (error) {
    console.error('Failed to fetch test problems:', error);
  }

  return {
    props: {
      initialProblems: testProblems || dummyProblems.default,
    },
  };
};

interface TestProps {
  initialProblems: Problem[];
}
interface ProblemAnswer {
  problemId: string;
  userAnswer: string; // 사용자의 선택
  problemAnswer: string; // 문제의 정답
  solvedTime?: number; // 풀이 시간 (선택적)
}

export default function Test({ initialProblems }: TestProps) {
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
  const [totalSolvedTime, setTotalSolvedTime] = useState<number>(0);
  const currentProblem = problems[currentProblemIndex];
  const [isMobile, setIsMobile] = useState(false); // 초기값 false로 설정
  const [loading, setLoading] = useState(false);

  console.log(answers);

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

  // 문제 풀이 로직 ///////////////////////////////////////////////////////////////////////////
  const updateAnswers = (
    problemIndex: string,
    updatedFields: Partial<ProblemAnswer>,
  ) => {
    setAnswers((prevAnswers) => {
      const updatedAnswers = prevAnswers.map((answer) =>
        answer.problemId === problemIndex
          ? { ...answer, ...updatedFields }
          : answer,
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

      updateAnswers(problems[currentProblemIndex].problemId, {
        solvedTime: (answers[currentProblemIndex]?.solvedTime || 0) + timeSpent,
      });
    };
  }, [currentProblemIndex]);

  const progressPercentage = useMemo(() => {
    return problems.length > 0
      ? Math.floor((solvedCount / problems.length) * 100)
      : 100;
  }, [solvedCount, problems.length]);

  const handleAnswer = (problemIndex: string, userAnswer: string) => {
    updateAnswers(problemIndex, { userAnswer });
  };

  const handleSubmit = async () => {
    const timeSpent = Math.floor((Date.now() - startTime) / 1000);

    updateAnswers(problems[currentProblemIndex].problemId, {
      solvedTime: (answers[currentProblemIndex]?.solvedTime || 0) + timeSpent,
    });

    const problemSolveRequests = answers.map((answer) => ({
      problemId: answer.problemId.toString(),
      userAnswer: answer.userAnswer,
      solvedTime: answer.solvedTime || 0,
    }));

    try {
      setLoading(true); // 로딩 시작
      const res = await postTestProblemGradingAPI(
        totalSolvedTime,
        problemSolveRequests,
      );
      console.log('서버 응답: ', res);

      dispatch(
        levelUp({ level: 2, levelImage: '/images/glu_character_shadow.png' }),
      );

      setLoading(false); // 로딩 종료
      router.push(`/test/result/${res?.data?.testId}`);
    } catch (error) {
      console.error('정답 제출 중 오류 발생:', error);
    }
  };

  // 문제 네비게이션 ///////////////////////////////////////////////////////////////////////////
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

  // 렌더링 로직 ///////////////////////////////////////////////////////////////////////////
  if (loading) {
    return (
      <div className={styles.container}>
        <Loading size="large" showText />
      </div>
    ); // 로딩 중일 때 표시할 메시지
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
            />
            <div className={styles['problem-content']}>
              <ProblemContentText problemContent={currentProblem?.content} />
              {currentProblem.questionType.code === 'QT02' && (
                <ProblemInputField
                  placeholder={
                    Array.isArray(currentProblem.metadata.options)
                      ? currentProblem.metadata.options.join(', ') // string[]일 경우, 문자열로 변환 (쉼표로 연결된 문자열)
                      : currentProblem.metadata.options // string일 경우 그대로 사용
                  }
                  initialAnswer={answers[currentProblemIndex].userAnswer}
                  problemId={answers[currentProblemIndex]?.problemId}
                  onTestProblemAnswer={handleAnswer}
                />
              )}
              {currentProblem.questionType.code !== 'QT02' && (
                <ProblemOptionList
                  problemOptions={
                    Array.isArray(currentProblem.metadata.options)
                      ? currentProblem.metadata.options // string[]일 경우
                      : [currentProblem.metadata.options] // string일 경우 배열로 변환
                  }
                  problemId={answers[currentProblemIndex]?.problemId}
                  selectedOption={answers[currentProblemIndex]?.userAnswer}
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
          <ProblemMemoManager problemId={currentProblem.problemId} />
        </div>
      </div>
    </div>
  );
}
