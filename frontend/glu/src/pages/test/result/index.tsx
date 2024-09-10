import { useState, useEffect } from 'react';
import { SolvedProblem, SolvedProblemType } from '@/types/ProblemTypes';
import dummyResults from '@/mock/dummyResults.json';
import RadarChart from '@/components/problem/result/radarChart';
import styles from './testResult.module.css';

interface ApiResponse {
  totalCorrectCount: number;
  totalSolvedTime: number; // 초 단위
  acquiredScore: number;
  totalScore: number;
  isStageUp: boolean;
  stageUpUrl: string;
  problemTypeList: SolvedProblemType[];
  problemList: SolvedProblem[];
}

export default function TestResult() {
  const [, setTotalCorrectCount] = useState<number | null>(null);
  const [totalSolvedTime, setTotalSolvedTime] = useState<number | null>(null);
  const [problemTypeList, setProblemTypeList] = useState<SolvedProblemType[]>(
    [],
  );
  const [problemList, setProblemList] = useState<SolvedProblem[]>([]);
  const [acquiredScore, setAcquiredScore] = useState<number | null>(null);
  const [totalScore, setTotalScore] = useState<number | null>(null);
  const [, setIsStageUp] = useState<boolean>(false);
  const [, setStageUpUrl] = useState<string>('');

  useEffect(() => {
    const fetchData = async () => {
      const response: ApiResponse = await new Promise((resolve) => {
        setTimeout(() => {
          resolve(dummyResults);
        }, 1000);
      });

      setTotalCorrectCount(response.totalCorrectCount);
      setTotalSolvedTime(response.totalSolvedTime);
      setProblemTypeList(response.problemTypeList);
      setProblemList(response.problemList);
      setAcquiredScore(response.acquiredScore);
      setTotalScore(response.totalScore);
      setIsStageUp(response.isStageUp);
      setStageUpUrl(response.stageUpUrl);
    };

    fetchData();
  }, []);

  const formatTime = (totalSeconds: number | null) => {
    if (totalSeconds === null) {
      return '--분 --초'; // null일 경우 기본 메시지 반환
    }

    const minutes = Math.floor(totalSeconds / 60);
    const seconds = totalSeconds % 60;
    return `${minutes}분 ${seconds}초`;
  };

  const renderProblemRows = (startIndex: number, endIndex: number) => (
    <>
      <tr>
        {problemList.slice(startIndex, endIndex).map((problem, index) => (
          <td key={problem.problemId}>{startIndex + index + 1}</td>
        ))}
      </tr>
      <tr>
        {problemList.slice(startIndex, endIndex).map((problem) => (
          <td key={problem.problemId}>{problem.isCorrect ? '✔️' : '❌'}</td>
        ))}
      </tr>
    </>
  );

  return (
    <div className={styles.container}>
      <h2 className={styles['page-title']}>테스트 결과</h2>
      <div className={styles['result-container']}>
        <div className={styles['result-wrapper']}>
          <div className={styles['result-item-row']}>
            <h5 className={styles['item-title']}>내 점수</h5>
            <div
              className={`${styles['item-content']} ${styles['item-content-text']}`}
            >
              {acquiredScore}/{totalScore}
            </div>
          </div>
          <div className={styles['result-item-row']}>
            <h5 className={styles['item-title']}>걸린 시간</h5>
            <p
              className={`${styles['item-content']} ${styles['item-content-text']}`}
            >
              {formatTime(totalSolvedTime)}
            </p>
          </div>
          <div className={styles['result-item']}>
            <h5 className={styles['item-title']}>맞힌 문제</h5>
            <div className={styles['item-content']}>
              <table
                className={`${styles['item-content']} ${styles['result-table']}`}
              >
                <tbody>
                  {renderProblemRows(0, 5)}
                  {renderProblemRows(5, 10)}
                  {renderProblemRows(10, 15)}
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div className={styles['result-wrapper']}>
          <div className={styles['result-item']}>
            <h5 className={styles['item-title']}>영역별 점수</h5>
            <div className={styles.canvasWrapper}>
              {/* RadarChart 컴포넌트 */}
              <RadarChart problemTypeList={problemTypeList} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
