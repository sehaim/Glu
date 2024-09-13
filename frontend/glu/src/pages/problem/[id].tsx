/* eslint-disable prettier/prettier */
import { useEffect, useState } from 'react';
import dummyImageProblem from '@/mock/dummyImageProblem.json'; // Import dummyProblem data
import { ProblemLevel, ProblemOption, ProblemType } from '@/types/ProblemTypes';
import ProblemHeader from '@/components/problem/problemHeader';
import ProblemContentText from '@/components/problem/problemContentText';
import ProblemContentImage from '@/components/problem/problemContentImage';
import ProblemOptionList from '@/components/problem/problemOptionList';
import PrimaryButton from '@/components/common/buttons/primaryButton';
import ProblemMemoManager from '@/components/problem/problemMemoManager';
import styles from './problem.module.css';
import { Memo } from '@/types/MemoTypes';

interface ProblemResponse {
  problemId: number;
  title: string;
  content: string;
  problemOptions: ProblemOption[];
  solution: string;
  problemLevel: ProblemLevel;
  problemType: ProblemType;
}

export default function Test() {
  const [problem, setProblem] = useState<ProblemResponse | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [selectedOption, setSelectedOption] = useState<number>(0); // State for selected option
  const [startTime, setStartTime] = useState<number>(0); // Start time state
  const [, setElapsedTime] = useState<number>(0); // Elapsed time state
  // 기존에 있던 dummyMemo를 상태로 변경
  const [memoList, setMemoList] = useState<Memo[]>([
    { memoId: 1, content: 'This is the first memo content.' },
    { memoId: 2, content: 'This is the second memo content.' },
    { memoId: 3, content: 'This is the third memo content.' },
    { memoId: 4, content: 'This is the fourth memo content.' },
  ]);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      const response: ProblemResponse = await new Promise((resolve) => {
        setTimeout(() => {
          return resolve(dummyImageProblem);
        }, 1000);
      });

      setProblem(response);
      setLoading(false);
      setStartTime(Date.now()); // 타이머 시작 (현재 시간 기록)
    };

    fetchData();
  }, []);

  const handleOptionClick = (optionIndex: number) => {
    setSelectedOption(optionIndex);
  };

  const handleSubmit = () => {
    const endTime = Date.now(); // 제출 시점의 시간
    const timeTaken = Math.floor((endTime - startTime) / 1000); // 경과 시간 계산 (초 단위)
    setElapsedTime(timeTaken); // 경과 시간 상태 업데이트
    setStartTime(Date.now());
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

  // 로딩 상태에서 undefined인 경우 처리
  if (loading) {
    return <div>로딩 중...</div>;
  }

  // problem이 null이 아닌지 확인
  if (!problem) {
    return <div>문제를 불러오는 중 오류가 발생했습니다.</div>;
  }

  return (
    <div className={styles.container}>
      <ProblemHeader
        problemLevel={problem.problemLevel.name}
        problemType={problem.problemType.name}
        problemTitle={problem.title}
      />
      <div className={styles['problem-content']}>
        {problem.problemType?.problemTypeDetailCode === '0' && (
          <ProblemContentImage
            imageUrl={problem.content}
            altText={problem.title || '문제 이미지'}
          />
        )}
        {problem.problemType?.problemTypeDetailCode !== '0' && (
          <ProblemContentText problemContent={problem.content} />
        )}
        <ProblemOptionList
          problemOptions={problem.problemOptions}
          selectedOption={selectedOption}
          onSingleProblemAnswer={handleOptionClick}
        />
      </div>
      {/* 제출하기 버튼 */}
      <div className={styles['problem-button-list']}>
        <div />
        <PrimaryButton size="medium" label="제출하기" onClick={handleSubmit} />
      </div>

      <ProblemMemoManager memoList={memoList} onSaveMemo={handleMemoSave} />
    </div>
  );
}
