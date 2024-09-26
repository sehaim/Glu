/* eslint-disable prettier/prettier */
import { useEffect, useState } from 'react';
import dummyImageProblem from '@/mock/dummyImageProblem.json';
import { ProblemLevel, ProblemOption, ProblemType } from '@/types/ProblemTypes';
import ProblemHeader from '@/components/problem/problemHeader';
import ProblemContentText from '@/components/problem/problemContentText';
import ProblemOptionList from '@/components/problem/problemOptionList';
import PrimaryButton from '@/components/common/buttons/primaryButton';
import ProblemMemoManager from '@/components/problem/problemMemoManager';
import { Memo } from '@/types/MemoTypes';
import { postSingleProblemGrading } from '@/utils/problem/problem';
import ProblemImageOptionList from '@/components/problem/problemImageOptionList';
import throttle from 'lodash/throttle';
import styles from './problem.module.css';

interface ProblemResponse {
  problemId: number;
  title: string;
  content: string;
  problemOptions: ProblemOption[];
  solution: string;
  problemLevel: ProblemLevel;
  problemType: ProblemType;
}

// getServerSideProps를 이용하여 SSR로 데이터 가져오기
export async function getServerSideProps() {
  // 서버에서 데이터를 가져오는 로직
  const problemData: ProblemResponse = await new Promise((resolve) => {
    setTimeout(() => {
      resolve(dummyImageProblem);
    }, 1000);
  });

  // 가져온 데이터를 props로 넘겨서 SSR로 렌더링
  return {
    props: {
      problemData,
    },
  };
}

interface TestProps {
  problemData: ProblemResponse;
}

export default function Test({ problemData }: TestProps) {
  const [problem] = useState<ProblemResponse>(problemData);
  const [answer, setAnswer] = useState<string>('');
  const [startTime, setStartTime] = useState<number>(Date.now());
  const [, setElapsedTime] = useState<number>(0);
  const [isMobile, setIsMobile] = useState(false); // 초기값 false로 설정

  const [memoList, setMemoList] = useState<Memo[]>([
    { memoId: 1, content: 'This is the first memo content.' },
    { memoId: 2, content: 'This is the second memo content.' },
    { memoId: 3, content: 'This is the third memo content.' },
    { memoId: 4, content: 'This is the fourth memo content.' },
  ]);

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

  const handleAnswer = (userAnswer: string) => {
    setAnswer(userAnswer);
  };

  const handleSubmit = async () => {
    const endTime = Date.now(); // 제출 시점의 시간
    const timeTaken = Math.floor((endTime - startTime) / 1000); // 경과 시간 계산 (초 단위)
    setElapsedTime(timeTaken); // 경과 시간 상태 업데이트
    setStartTime(Date.now());

    if (problem) {
      try {
        // Call the postSingleProblemGrading function to submit the answer and time
        const response = await postSingleProblemGrading(
          problem.problemId,
          answer,
          timeTaken,
        );
        console.log('Submission response:', response);
      } catch (error) {
        console.error('Error submitting answer:', error);
      }
    }
  };

  const handleMemoSave = (newMemo: Memo) => {
    setMemoList((prevMemoList) => {
      // 만약 기존 메모를 수정하는 경우 memoId를 비교하여 업데이트
      const memoIndex = prevMemoList.findIndex(
        (memo) => memo.memoId === newMemo.memoId,
      );
      if (memoIndex > -1) {
        // 기존 메모 수정
        const updatedMemoList = [...prevMemoList];
        updatedMemoList[memoIndex] = newMemo;
        return updatedMemoList;
      }
      // 새로운 메모 추가
      return [...prevMemoList, newMemo];
    });
  };

  return (
    <div className={styles.container}>
      <div className={styles['problem-container']}>
        <div className={styles.problem}>
          <ProblemHeader
            problemLevel={problem.problemLevel.name}
            problemType={problem.problemType.name}
            problemTitle={problem.title}
            problemId={problem.problemId}
            problemLike={false}
          />
          <div className={styles['problem-content']}>
            <ProblemContentText problemContent={problem.content} />
            {problem.problemType?.problemTypeDetailCode === '0' && (
              <ProblemImageOptionList
                problemOptions={problem.problemOptions}
                selectedOption={answer}
                onSingleProblemAnswer={handleAnswer}
              />
            )}
            {problem.problemType?.problemTypeDetailCode !== '0' && (
              <ProblemOptionList
                problemOptions={problem.problemOptions}
                selectedOption={answer}
                onSingleProblemAnswer={handleAnswer}
              />
            )}
          </div>
          <div className={styles['problem-button-list']}>
            <div />
            <PrimaryButton
              size="small"
              label="제출하기"
              onClick={handleSubmit}
            />
          </div>
        </div>
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
