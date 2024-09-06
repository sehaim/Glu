/* eslint-disable @next/next/no-img-element */
import { useRouter } from 'next/router';
import PrimaryButton from '@/components/common/buttons/primaryButton';
import styles from './home.module.css';

export default function Home() {
  const router = useRouter();

  const handleStartButton = () => {
    router.push('/signup');
  };

  return (
    <div className={styles.container}>
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
        <PrimaryButton
          label="Glu 시작하기"
          size="large"
          onClick={handleStartButton}
        />
      </div>
      <img
        className={styles['main-character']}
        src="/images/glu_character.png"
        alt="Glu Character"
      />
    </div>
  );
}
