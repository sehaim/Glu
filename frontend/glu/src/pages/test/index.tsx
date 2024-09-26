/* eslint-disable prettier/prettier */
import { useRouter } from 'next/router';
import { useEffect, useState, useMemo } from 'react';
import PrimaryButton from '@/components/common/buttons/primaryButton';
import RadarChart from '@/components/problem/result/radarChart';
import dummyPreviousTest from '@/mock/dummyPreviousTest.json';
import { PreviousSolvedProblemType } from '@/types/ProblemTypes';
import { formatTime, transformProblemType } from '@/utils/problem/result';
import Image from 'next/image';
import { useSelector } from 'react-redux';
import { RootState } from '@/store';
import styles from './test.module.css';

interface ApiResponse {
  testId: number;
  correctCount: number;
  totalSolveTime: number; // 초 단위
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
  const username = useSelector((state: RootState) => state.auth.nickname);

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

  // useMemo를 사용해 problemTypeList가 변경될 때만 변환된 리스트를 재계산
  const transformedProblemTypeList = useMemo(
    () => transformProblemType(problemTypeList),
    [problemTypeList],
  );

  const handleButtonClick = () => {
    router.push('/test/problems');
  };

  return (
    <div className={styles.container}>
      <div className={styles['content-wrapper-row']}>
        <div>
          <h2 className={styles['page-title']}>종합 테스트 추천</h2>
          <p>
            <span id={styles.username}>{username}</span>님을 위한 종합
            테스트입니다.
            <br />
            총 15문제로, 모든 유형이 포함되어 나의 문해력을 종합적으로
            평가합니다.
            <br />
            지금 바로 시작해보세요!
          </p>
        </div>
        <div className={styles['button-wrapper']}>
          <PrimaryButton
            size="large"
            label="종합테스트 시작"
            onClick={handleButtonClick}
          />
        </div>
      </div>

      <div className={styles['content-wrapper-column']}>
        <h3 className={styles['page-subTitle']}>나의 이전 테스트</h3>
        <div className={styles['last-test-wrapper']}>
          <div className={styles['last-test']}>
            <div className={styles['last-test-items-column']}>
              <div className={styles['last-test-item']}>
                <p className={styles['last-test-item-title']}>내 점수</p>
                <div className={styles['last-test-item-content']}>
                  {correctCount} / 15
                </div>
              </div>
              <div className={styles['last-test-item']}>
                <p className={styles['last-test-item-title']}>걸린 시간</p>
                <div className={styles['last-test-item-content']}>
                  {formatTime(totalSolveTime)}
                </div>
              </div>
            </div>
            <div className={styles['last-test-items-column']}>
              <div className={styles['last-test-item-column']}>
                <p className={styles['last-test-item-title']}>영역별 점수</p>
                <div className={styles['last-test-item-content-column']}>
                  <RadarChart problemTypeList={transformedProblemTypeList} />
                </div>
              </div>
            </div>
          </div>
          <Image
            src="/images/glu_character_shadow.png"
            alt="Glu Character"
            width={300}
            height={356}
          />
        </div>
      </div>
    </div>
  );
}
