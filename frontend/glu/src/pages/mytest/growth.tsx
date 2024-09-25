import MytestGrade from '@/components/mytest/mytestGrade';
import MytestAttendance from '@/components/mytest/mytestAttendance';
import { getUserInfoAPI } from '@/utils/user/mypage';
import { MypageUser } from '@/types/UserTypes';
import styles from './mytest.module.css';

export async function getServerSideProps() {
  // 서버에서 회원정보 API 호출
  const userInfo = await getUserInfoAPI();
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
  console.log(userInfo);
  return (
    <div className={`${styles.section} ${styles.row}`}>
      <MytestGrade />
      <MytestAttendance />
    </div>
  );
}
