import MytestGrade from '@/components/mytest/mytestGrade';
import MytestAttendance from '@/components/mytest/mytestAttendance';
import { getUserInfoAPI, getAttendanceAPI } from '@/utils/user/mypage';
import { MypageUser } from '@/types/UserTypes';
import { GetServerSidePropsContext } from 'next';
import styles from './mytest.module.css';

export async function getServerSideProps(context: GetServerSidePropsContext) {
  // 서버에서 회원정보 API 호출
  const userInfo = await getUserInfoAPI(context);
  const attendance = await getAttendanceAPI(context);
  // 데이터를 props로 페이지 컴포넌트에 전달
  return {
    props: {
      userInfo,
    },
  };
}

interface MytestGrowthPageProps {
  userInfo: MypageUser;
}

export default function MytestGrowthPage({ userInfo }: MytestGrowthPageProps) {
  return (
    <div className={`${styles.section} ${styles.row}`}>
      <MytestGrade />
      <MytestAttendance />
    </div>
  );
}
