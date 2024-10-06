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
import { ComprehesiveTestRecord } from '@/types/TestTypes';
import styles from './mytest.module.css';

export const getServerSideProps: GetServerSideProps = async (context) => {
  // 서버에서 회원정보 API 호출
  const userInfo = await getUserInfoAPI(context);
  const attendances = await getAttendanceAPI(context);

  // 서버에서 종합 테스트 기록 조회 API 호출
  const testData = await getSolvedComprehensiveTestAPI(context, 1, 5);
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
  testList: ComprehesiveTestRecord[];
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
      <MytestGrade userInfo={userInfo} />
      <div className={styles.section}>
        <MytestAttendance
          attendances={attendances}
          attendanceRate={userInfo.attendanceRate}
          createDate={userInfo.createDate}
        />
        <MytestComprehensiveTestRecordList
          testList={testList}
          totalPages={totalPages}
        />
      </div>
    </div>
  );
}
