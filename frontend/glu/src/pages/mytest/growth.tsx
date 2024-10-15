import MytestGrade from '@/components/mytest/mytestGrade';
import MytestAttendance from '@/components/mytest/mytestAttendance';
import { getUserInfoAPI } from '@/utils/user/mypage';
import {
  getAttendanceAPI,
  getSolvedComprehensiveTestAPI,
} from '@/utils/user/mytest';
import { MypageUser, Attendances } from '@/types/UserTypes';
import { GetServerSideProps } from 'next';
import MytestComprehensiveTestRecordList from '@/components/mytest/mytestComprehensiveTestRecordList';
import { ComprehensiveTestRecord } from '@/types/TestTypes';
import { getCookie } from 'cookies-next';
import Head from 'next/head';
import styles from './mytest.module.css';

export const getServerSideProps: GetServerSideProps = async (context) => {
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

  // 서버에서 회원정보 API 호출
  const userInfo = await getUserInfoAPI(context);
  const attendances = await getAttendanceAPI(context);

  // 서버에서 종합 테스트 기록 조회 API 호출
  const testData = await getSolvedComprehensiveTestAPI(0, 5, context);
  const testList = testData?.content;
  const totalPages = testData?.totalPages;

  return {
    props: {
      userInfo,
      attendances,
      testList,
      totalPages,
    },
  };
};

interface MytestGrowthPageProps {
  userInfo: MypageUser;
  attendances: Attendances | null;
  testList: ComprehensiveTestRecord[];
  totalPages: number;
}

export default function MytestGrowthPage({
  userInfo,
  attendances,
  testList,
  totalPages,
}: MytestGrowthPageProps) {
  return (
    <div className={`${styles['growth-container']} ${styles.row}`}>
      <Head>
        <title>나의 학습 - 나의 성장</title>
        <link rel="icon" type="image/png" sizes="48x48" href="/favicon.png" />
        <link rel="icon" type="image/x-icon" href="/favicon.ico" />
      </Head>
      <MytestGrade userInfo={userInfo} />
      <div className={styles['growth-section']}>
        <MytestAttendance
          attendances={attendances}
          attendanceRate={userInfo.attendanceRate}
          createDate={userInfo.createDate}
        />
        <MytestComprehensiveTestRecordList
          initialTestList={testList}
          totalPages={totalPages}
        />
      </div>
    </div>
  );
}
