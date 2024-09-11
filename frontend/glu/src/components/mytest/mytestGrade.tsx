/* eslint-disable jsx-a11y/img-redundant-alt */
/* eslint-disable @next/next/no-img-element */
import styles from './mytestGrade.module.css';

export default function MytestGrade() {
  return (
    <div className={styles.container}>
      <div className={styles.section}>
        <div className={styles['section-title']}>내 캐릭터</div>
        <div className={styles['img-container']}>
          <img
            src="/images/glu_character_shadow.png"
            alt="character image"
            className={styles['character-img']}
          />
        </div>
        <div>경험치</div>
      </div>
      <div className={styles.section}>
        <div className={styles['section-title']}>영역별 점수</div>
        <div>삼각 그래프</div>
        <div>성적별 그래프</div>
      </div>
    </div>
  );
}
