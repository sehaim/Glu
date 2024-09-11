import { useRouter } from 'next/router';
import { useEffect, useState } from 'react';
import PrimaryButton from '@/components/common/buttons/primaryButton';
import RadarChart from '@/components/problem/result/radarChart';
import dummyPreviousTest from '@/mock/dummyPreviousTest.json'; // Import dummy data
import {
  PreviousSolvedProblemType,
  SolvedProblemType,
} from '@/types/ProblemTypes';
import styles from './test.module.css';

interface ApiResponse {
  testId: number;
  correctCount: number;
  totalSolveTime: number; // in seconds
  problemType: PreviousSolvedProblemType[];
}

export default function Test() {
  const router = useRouter();
  const [correctCount, setCorrectCount] = useState<number | null>(null);
  const [totalSolveTime, setTotalSolveTime] = useState<number | null>(null);
  const [problemTypeList, setProblemTypeList] = useState<
    PreviousSolvedProblemType[]
  >([]);
  const [, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true); // 데이터를 가져오는 동안 로딩 상태를 true로 설정
      const response: ApiResponse = await new Promise((resolve) => {
        setTimeout(() => {
          return resolve(dummyPreviousTest);
        }, 1000);
      });

      setCorrectCount(response.correctCount);
      setTotalSolveTime(response.totalSolveTime);
      setProblemTypeList(response.problemType);
      setLoading(false); // 데이터가 다 로드되면 로딩 상태를 false로 설정
    };

    fetchData();
  }, []);

  // Data transformation from PreviousSolvedProblemType[] to SolvedProblemType[]
  const transformedProblemTypeList: SolvedProblemType[] = problemTypeList.map(
    (item) => ({
      correctCount: item.correctCount,
      problemType: {
        problemTypeCode: item.problemTypeCode,
        name: item.name,
      },
    }),
  );

  const handleButtonClick = () => {
    router.push('/test/problems'); // Navigate to /test/problems when clicked
  };

  const formatTime = (totalSeconds: number | null) => {
    if (totalSeconds === null) {
      return '--분 --초'; // null일 경우 기본 메시지 반환
    }

    const minutes = Math.floor(totalSeconds / 60);
    const seconds = totalSeconds % 60;
    return `${minutes}분 ${seconds}초`;
  };

  return (
    <div className={styles.container}>
      {/* Test Recommendation Section */}
      <div className={styles['recommendation-container']}>
        <div>
          <h2 className={styles['page-title']}>종합 테스트 추천</h2>
          <p>
            김씨피님을 위한 종합 테스트입니다.
            <br />
            총 15문제로, 모든 유형이 포함되어 나의 문해력을 종합적으로
            평가합니다.
            <br />
            지금 바로 시작해보세요!
          </p>
        </div>
        <PrimaryButton
          size="large"
          label="종합테스트 시작"
          onClick={handleButtonClick}
        />
      </div>

      {/* Previous Test Results Section */}
      <div className={styles['recommendation-container']}>
        <div>
          <h3 className={styles['page-subTitle']}>나의 이전 테스트</h3>
          <div className={styles['last-test-info']}>
            <div className={styles['last-test-wrapper']}>
              <div className={styles['test-info-item-row']}>
                <p className={styles['info-item-title']}>내 점수</p>
                <div className={styles['info-item-content']}>
                  {correctCount} / 15
                </div>
              </div>
              <div className={styles['test-info-item-row']}>
                <p className={styles['info-item-title']}>걸린 시간</p>
                <div className={styles['info-item-content']}>
                  {formatTime(totalSolveTime)}
                </div>
              </div>
            </div>
            <div className={styles['test-info-item']}>
              <p className={styles['info-item-title']}>영역별 점수</p>
              <div className={styles['info-item-chart']}>
                <RadarChart problemTypeList={transformedProblemTypeList} />
              </div>
            </div>
          </div>
        </div>
        <img
          className={styles['main-character']}
          src="/images/glu_character_shadow.png"
          alt="Glu Character"
        />
      </div>
    </div>
  );
}
