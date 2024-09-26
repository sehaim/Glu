import MytestGrade from '@/components/mytest/mytestGrade';
import MytestAttendance from '@/components/mytest/mytestAttendance';
import { getUserInfoAPI, getAttendanceAPI } from '@/utils/user/mypage';
import { MypageUser, Attendances } from '@/types/UserTypes';
import { GetServerSideProps } from 'next';
import styles from './mytest.module.css';

export const getServerSideProps: GetServerSideProps = async (context) => {
  // 서버에서 회원정보 API 호출
  const userInfo = await getUserInfoAPI(context);
  const attendances = await getAttendanceAPI(context);

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
    <div className={`${styles.section} ${styles.row}`}>
      <MytestGrade userInfo={userInfo} />
      <MytestAttendance
        attendances={attendances}
        attendanceRate={userInfo.attendanceRate}
      />
    </div>
  );
}
