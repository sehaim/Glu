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
  const [loading, setLoading] = useState<boolean>(true); // 로딩 상태 추가
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
      setLoading(true); // 데이터를 가져오는 동안 로딩 상태를 true로 설정
      const response: ApiResponse = await new Promise((resolve) => {
        setTimeout(() => {
          return resolve(dummyResults);
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
      setLoading(false); // 데이터가 다 로드되면 로딩 상태를 false로 설정
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

  if (loading) {
    return <div>결과 로딩 중...</div>; // 로딩 중일 때 표시할 메시지
  }

  console.log(problemList);

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
      <div className={styles['solution-container']}>
        <h5 className={styles['item-title']}>문제 해설</h5>
        <div className={styles['solution-list']}>
          {problemList.map((problem, index) => (
            <div key={problem.problemId} className={styles['solution-item']}>
              <p className={styles['solution-title']}>
                {index + 1}. {problem.title}
              </p>
              <div className={styles['solution-content']}>
                <div className={styles['problem-content']}>
                  {problem.content}
                </div>
                <div className={styles['problem-option-list']}>
                  {problem?.problemOptions.map(
                    (
                      problemOption,
                      optionIndex, // index 대신 optionIndex 사용
                    ) => (
                      <p
                        key={problemOption.problemOptionId} // 고유 식별자로 problemOptionId 사용
                        className={styles['problem-option-item']}
                      >
                        {optionIndex + 1}. {problemOption.option}{' '}
                        {/* 옵션 번호는 optionIndex를 사용 */}
                      </p>
                    ),
                  )}
                </div>
              </div>
              <div className={styles['problem-solution']}>
                {problem.solution}
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
