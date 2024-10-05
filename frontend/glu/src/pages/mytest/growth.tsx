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
import styles from './mytest.module.css';

export const getServerSideProps: GetServerSideProps = async (context) => {
  // 서버에서 회원정보 API 호출
  const userInfo = await getUserInfoAPI(context);
  const attendances = await getAttendanceAPI(context);
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const TestList = await getSolvedComprehensiveTestAPI(context, 1, 5);

  // 데이터를 props로 페이지 컴포넌트에 전달
  return {
    props: {
      userInfo,
      attendances,
    },
  };
};

interface MytestGrowthPageProps {
  userInfo: MypageUser;
  attendances: Attendances | null;
}

export default function MytestGrowthPage({
  userInfo,
  attendances,
}: MytestGrowthPageProps) {
  return (
    <div className={`${styles.container} ${styles.row}`}>
      <MytestGrade userInfo={userInfo} />
      <div className={styles.section}>
        <MytestAttendance
          attendances={attendances}
          attendanceRate={userInfo.attendanceRate}
        />
        <MytestComprehensiveTestRecordList />
      </div>
    </div>
  );
}
