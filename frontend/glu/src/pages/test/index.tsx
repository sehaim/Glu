/* eslint-disable prettier/prettier */
import { GetServerSideProps } from 'next';
import { useRouter } from 'next/router';
import PrimaryButton from '@/components/common/buttons/primaryButton';
import RadarChart from '@/components/problem/result/radarChart';
import { PreviousSolvedProblemType } from '@/types/ProblemTypes';
import { formatTime, transformProblemType } from '@/utils/problem/result';
import Image from 'next/image';
import { useSelector } from 'react-redux';
import { RootState } from '@/store';
import dummyPreviousTest from '@/mock/dummyPreviousTest.json';
import { useMemo } from 'react';
import styles from './test.module.css';

interface ApiResponse {
  testId: number;
  correctCount: number;
  totalSolveTime: number; // 초 단위
  problemType: PreviousSolvedProblemType[];
}

interface TestProps {
  correctCount: number;
  totalSolveTime: number;
  problemTypeList: PreviousSolvedProblemType[];
}

export const getServerSideProps: GetServerSideProps = async () => {
  // TODO: axios로 변경 -> 실제 data fetch
  const response: ApiResponse = await new Promise((resolve) => {
    setTimeout(() => {
      resolve(dummyPreviousTest);
    }, 1000);
  });

  return {
    props: {
      correctCount: response.correctCount,
      totalSolveTime: response.totalSolveTime,
      problemTypeList: response.problemType,
    },
  };
};

export default function Test({
  correctCount,
  totalSolveTime,
  problemTypeList,
}: TestProps) {
  const router = useRouter();
  const username = useSelector((state: RootState) => state.auth.nickname);

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
