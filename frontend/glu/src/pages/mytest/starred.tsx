import MytestTestCardList from '@/components/mytest/mytestTestCardList';
import { getSolvedTypeTestAPI } from '@/utils/user/mytest';
import { ProblemType, SolvedProblemResponse } from '@/types/ProblemTypes';
import { GetServerSideProps } from 'next';
import styles from './mytest.module.css';

export const getServerSideProps: GetServerSideProps = async (context) => {
  const problemData01 = await getSolvedTypeTestAPI(
    'PT01',
    0,
    undefined,
    undefined,
    true,
    context,
  );

  const problemData02 = await getSolvedTypeTestAPI(
    'PT02',
    0,
    undefined,
    undefined,
    true,
    context,
  );

  const problemData03 = await getSolvedTypeTestAPI(
    'PT03',
    0,
    undefined,
    undefined,
    true,
    context,
  );

  const testDataList = [
    {
      problemType: { code: 'PT001', name: '어휘 및 문법' },
      problemData: problemData01,
    },
    {
      problemType: { code: 'PT002', name: '독해' },
      problemData: problemData02,
    },
    {
      problemType: { code: 'PT003', name: '추론' },
      problemData: problemData03,
    },
  ];

  return {
    props: {
      testDataList,
    },
  };
};

interface MytestStarredPageProps {
  testDataList: [
    { problemType: ProblemType; problemData: SolvedProblemResponse },
  ];
}

export default function MytestStarredPage({
  testDataList,
}: MytestStarredPageProps) {
  return (
    <div className={styles.container}>
      <div className={styles.section}>
        {testDataList.map((testData) => (
          <MytestTestCardList
            key={testData.problemType.code}
            testData={testData?.problemData}
            problemType={testData?.problemType}
            pageType="starred"
          />
        ))}
      </div>
    </div>
  );
}
