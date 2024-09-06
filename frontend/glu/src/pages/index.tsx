/* eslint-disable @next/next/no-img-element */
import styles from './home.module.css';
import TestButton from '@/components/common/buttons/testButton';

export default function Home() {
  return (
    <div className={styles.container}>
      <div className={styles.content}>
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
          <TestButton />
        </div>
        <img
          id={styles['main-character']}
          src="/images/glu_character.png"
          alt="Glu Character"
        />
      </div>
    </div>
  );
}
