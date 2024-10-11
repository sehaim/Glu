/* eslint-disable prettier/prettier */
import { useRouter } from 'next/router';
import PrimaryButton from '@/components/common/buttons/primaryButton';
import RadarGraph from '@/components/common/graphs/radarGraph';
import { PreviousSolvedTestType } from '@/types/TestTypes';
import {
  formatTime,
  getPreviousTestAPI,
  transformProblemType,
} from '@/utils/problem/result';
import Image from 'next/image';
import { useSelector } from 'react-redux';
import { RootState } from '@/store';
import { GetServerSideProps, GetServerSidePropsContext } from 'next';
import Head from 'next/head';
import { getCookie } from 'cookies-next';
import { jwtDecode } from 'jwt-decode';
import { sweetalertError } from '@/utils/common';
import { useEffect } from 'react';
import styles from './test.module.css';

export const getServerSideProps: GetServerSideProps = async (
  context: GetServerSidePropsContext,
) => {
  const { req, res } = context;
  const accessToken = getCookie('accessToken', { req, res });

  if (!accessToken) {
    return {
      redirect: {
        destination: '/',
        permanent: false,
      },
    };
  }

  const decodedToken: never = jwtDecode(accessToken);
  const { isFirst } = decodedToken;

  const resData = await getPreviousTestAPI(context);

  if (!resData) {
    return {
      props: {
        isFirst,
        totalCorrectCount: 0,
        totalSolveTime: 0,
        gradingResultByTypeList: [],
      },
    };
  }

  return {
    props: {
      isFirst,
      totalCorrectCount: resData.totalCorrectCount,
      totalSolveTime: resData.totalSolvedTime,
      gradingResultByTypeList: resData.gradingResultByTypeList,
    },
  };
};

interface TestProps {
  isFirst: boolean;
  totalCorrectCount: number;
  totalSolveTime: number;
  gradingResultByTypeList: PreviousSolvedTestType[];
}

export default function Test({
  isFirst,
  totalCorrectCount,
  totalSolveTime,
  gradingResultByTypeList,
}: TestProps) {
  // 레벨테스트 응시 전 페이지 리다이렉트
  useEffect(() => {
    const showAlert = async () => {
      const result = await sweetalertError(
        '레벨테스트 응시',
        '레벨테스트 응시 후 종합테스트를 추천받을 수 있습니다.',
      );

      if (result.isConfirmed) {
        window.location.href = '/home';
      }
    };

    if (isFirst) {
      showAlert();
    }
  }, [isFirst]);

  const router = useRouter();
  const username = useSelector((state: RootState) => state.auth.nickname);
  const { correctCount, solveTime } = {
    correctCount: totalCorrectCount,
    solveTime: totalSolveTime,
  };
  const transformedProblemTypeList = transformProblemType(
    gradingResultByTypeList,
  );

  const data = transformedProblemTypeList.map((item) => ({
    axis: item.problemType.name,
    value: item.correctCount,
  }));

  const handleButtonClick = () => {
    router.push('/test/problems');
  };

  return (
    <div className={styles.container}>
      <Head>
        <title>종합 테스트 추천</title>
        <meta property="og:title" content="종합 테스트 추천" />
        <meta
          property="og:description"
          content="총 15문제로, 모든 유형이 포함되어 나의 문해력을 종합적으로 평가합니다."
        />
        <meta property="og:type" content="website" />
      </Head>

      <div className={styles['content-wrapper-row']}>
        <div className={styles['content-wrapper']}>
          <h2 className={styles['page-title']}>종합 테스트 추천</h2>
          <p className={styles.content}>
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
                  {formatTime(solveTime)}
                </div>
              </div>
            </div>
            <div className={styles['last-test-items-column']}>
              <div className={styles['last-test-item-column']}>
                <p className={styles['last-test-item-title']}>영역별 점수</p>
                <div className={styles['last-test-item-content-column']}>
                  {transformedProblemTypeList && (
                    <RadarGraph data={data} maxScore={5} />
                  )}
                </div>
              </div>
            </div>
          </div>
          <Image
            src="/images/character/glu_character_shadow.png"
            alt="Glu Character"
            width={300}
            height={356}
            priority
          />
        </div>
      </div>
    </div>
  );
}
