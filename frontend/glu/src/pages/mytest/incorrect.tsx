import { GetServerSideProps } from 'next';
import styles from './mytest.module.css';
import { getSolvedTypeTestAPI } from '@/utils/user/mytest';

export const getServerSideProps: GetServerSideProps = async (context) => {
  const problemData01 = await getSolvedTypeTestAPI(
    'PT01',
    0,
    'WRONG',
    undefined,
    undefined,
    context,
  );

  const problemData02 = await getSolvedTypeTestAPI(
    'PT02',
    0,
    'WRONG',
    undefined,
    undefined,
    context,
  );

  const problemData03 = await getSolvedTypeTestAPI(
    'PT03',
    0,
    'WRONG',
    undefined,
    undefined,
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

interface MytestCorrectPageProps {
  testDataList: [
    { problemType: ProblemType; problemData: SolvedProblemResponse },
  ];
}

export default function MytestIncorrectPage() {
  return (
    <div className={styles.container}>
      <div>못 푼 문제</div>
    </div>
  );
}
