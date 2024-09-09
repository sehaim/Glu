import styles from './problem.module.css';
import { BsStar } from 'react-icons/bs';
import { BsStarFill } from 'react-icons/bs';

export default function Problem() {
  return (
    <div className={styles.container}>
      <div className={styles['status-container']}>
        <div className={styles['status-level']}>LV.2</div>
        <div className={styles['status-like-wrapper']}>
          <div className={styles['status-like-icon']}>
            <BsStar />
          </div>
        </div>
      </div>
      <div className={styles['info-container']}>
        <div className={styles['problem-type']}>어휘</div>
        <div className={styles['problem-instruction']}>
          다음 중 비슷한 문장을 고르세요.
        </div>
      </div>
      <div className={styles['problem-content']}>
        <div className={styles['problem-text']}>
          정기적인 운동은 신체 건강에 매우 중요한 역할을 합니다. 운동은 심혈관
          건강을 개선하고, 근육을 강화하며, 체중을 관리하는 데 도움을 줍니다.
          또한, 운동은 스트레스를 줄이고, 정신 건강을 향상시키는 데도 큰 도움이
          됩니다. 특히, 하루에 최소 30분 이상 운동하는 것이 권장됩니다.
        </div>
        <div>선택지</div>
      </div>
    </div>
  );
}
