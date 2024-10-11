/* eslint-disable @next/next/no-img-element */
import { GetServerSideProps } from 'next';
import { useRouter } from 'next/router';
import Image from 'next/image';
import PrimaryButton from '@/components/common/buttons/primaryButton';
import { getCookie } from 'cookies-next';
import styles from './home.module.css';

// 서버 사이드에서 로그인 상태를 확인하는 getServerSideProps 함수
export const getServerSideProps: GetServerSideProps = async (context) => {
  const { req, res } = context;
  const accessToken = getCookie('accessToken', { req, res });

  if (accessToken) {
    return {
      redirect: {
        destination: '/home', // accessToken이 존재하면 /home으로 리다이렉트
        permanent: false, // 영구적인 리다이렉트는 아님(로그인 상태가 계속 변할 수 있기 때문에)
      },
    };
  }

  return {
    props: {}, // 로그인되지 않은 경우 페이지 렌더링
  };
};

export default function MainPage() {
  const router = useRouter();

  const handleButton = () => {
    router.push('/signup');
  };

  return (
    <div className={styles.container}>
      <div className={styles.section}>
        <div className={styles['describe-section']}>
          <div className={styles['section-title']}>
            <div>글루</div>
            <div>어린이 문해력</div>
            <div>프로그램</div>
          </div>
          <div className={styles['section-describe']}>
            내 문해력 수준에 맞는 문제 추천 서비스를 통해 쉽고 재미있게 문해력을
            향상시킬 수 있습니다.
          </div>
          <div className={styles['button-container']}>
            <PrimaryButton
              label="Glu 시작하기"
              size="large"
              onClick={handleButton}
            />
          </div>
        </div>
        <Image
          className={styles['main-character']}
          src="/images/character/glu_character_shadow.png"
          alt="Glu Character"
          width={300}
          height={356}
          priority
        />
      </div>
    </div>
  );
}
