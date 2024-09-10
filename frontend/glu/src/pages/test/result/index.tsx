import { useState, useEffect, useRef } from 'react';
import { SolvedProblem as Problem } from '@/types/ProblemTypes';
import dummyResults from '@/mock/dummyResults.json';
// eslint-disable-next-line import/no-extraneous-dependencies
import {
  Chart,
  RadarController,
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend,
} from 'chart.js';
import styles from './testResult.module.css';

// Chart.js 모듈 등록
Chart.register(
  RadarController,
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend,
);

interface ApiResponse {
  totalCorrectCount: number;
  totalSolvedTime: number; // 초 단위
  acquiredScore: number;
  totalScore: number;
  isStageUp: boolean;
  stageUpUrl: string;
  problemList: Problem[];
}

export default function TestResult() {
  const [, setTotalCorrectCount] = useState<number | null>(null);
  const [totalSolvedTime, setTotalSolvedTime] = useState<number | null>(null);
  const [problemList, setProblemList] = useState<Problem[]>([]);
  const [acquiredScore, setAcquiredScore] = useState<number | null>(null);
  const [totalScore, setTotalScore] = useState<number | null>(null);
  const [, setIsStageUp] = useState<boolean>(false);
  const [, setStageUpUrl] = useState<string>('');

  const chartRef = useRef<HTMLCanvasElement>(null);
  const chartInstanceRef = useRef<Chart | null>(null); // Chart 인스턴스를 저장하는 ref

  useEffect(() => {
    const fetchData = async () => {
      const response: ApiResponse = await new Promise((resolve) => {
        setTimeout(() => {
          resolve(dummyResults);
        }, 1000);
      });

      setTotalCorrectCount(response.totalCorrectCount);
      setTotalSolvedTime(response.totalSolvedTime);
      setProblemList(response.problemList);
      setAcquiredScore(response.acquiredScore);
      setTotalScore(response.totalScore);
      setIsStageUp(response.isStageUp);
      setStageUpUrl(response.stageUpUrl);
    };

    fetchData();

    if (chartRef.current) {
      const ctx = chartRef.current.getContext('2d');

      // 기존 차트가 있으면 삭제
      if (chartInstanceRef.current) {
        chartInstanceRef.current.destroy();
      }

      if (ctx) {
        const data = {
          labels: ['독해', '어휘 및 문법', '추론'], // 레이다 차트의 각 축
          datasets: [
            {
              label: '영역별 점수',
              data: [70, 50, 30], // 예시 데이터
              backgroundColor: 'rgba(255, 99, 132, 0.2)',
              borderColor: 'rgba(255, 99, 132, 1)',
              borderWidth: 1,
            },
          ],
        };

        chartInstanceRef.current = new Chart(ctx, {
          type: 'radar',
          data,
          options: {
            responsive: true, // 반응형 차트
            maintainAspectRatio: false, // 차트의 비율을 유지하지 않음
            scales: {
              r: {
                angleLines: {
                  display: true,
                },
                suggestedMin: 0,
                suggestedMax: 100,
              },
            },
          },
        });
      }
    }

    return () => {
      // 컴포넌트가 언마운트될 때 차트를 삭제
      if (chartInstanceRef.current) {
        chartInstanceRef.current.destroy();
      }
    };
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
              {/* Chart.js 캔버스 */}
              <canvas ref={chartRef} className={styles.chartCanvas} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
