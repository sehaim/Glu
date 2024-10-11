/* eslint-disable jsx-a11y/img-redundant-alt */
/* eslint-disable @next/next/no-img-element */
import { MypageUser } from '@/types/UserTypes';
import styles from './mytestGrade.module.css';
import BarGraph from '../common/graphs/barGraph';
import RadarGraph from '../common/graphs/radarGraph';

interface MytestGradeProps {
  userInfo: MypageUser;
}

export default function MytestGrade({ userInfo }: MytestGradeProps) {
  const problemTypeList = userInfo.problemTypeList.map((item) => ({
    name: item.type.name,
    level: item.level,
    score: item.score,
  }));

  const data = problemTypeList.map((item) => ({
    axis: item.name,
    value: item.level,
  }));

  return (
    <div className={styles.container}>
      <div className={styles.section}>
        <div className={styles['section-title']}>내 캐릭터</div>
        <div className={styles['img-container']}>
          <img
            src={userInfo.imageUrl}
            alt="character image"
            className={styles['character-img']}
          />
        </div>
        <div className={styles['flex-line']}>
          <div className={styles['exp-title']}>{userInfo.stage} 단계</div>
          <div className={styles['exp-container']}>
            <BarGraph maxScore={100} currentScore={userInfo.exp} />
          </div>
        </div>
      </div>
      <div className={styles.section}>
        <div className={styles['section-title']}>영역별 점수</div>
        <div className={styles['triangle-graph-container']}>
          <RadarGraph data={data} maxScore={7} />
        </div>
        <div>
          {problemTypeList.map((item) => {
            return (
              <div className={styles['bar-graph-item']} key={item.name}>
                <div className={styles['problem-name']}>{item.name}</div>
                <div
                  className={styles['flex-line']}
                  id={styles['bar-graph-line']}
                >
                  <span className={styles.level}>LV.{item.level}</span>
                  <div className={styles['exp-container']}>
                    <BarGraph maxScore={100} currentScore={item.score} />
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
}
