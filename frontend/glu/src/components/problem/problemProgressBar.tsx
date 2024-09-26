import Image from 'next/image';
import styles from './problemProgressBar.module.css';

interface ProblemProgressBarProps {
  progressPercentage: number;
}

export default function ProblemProgressBar({
  progressPercentage,
}: ProblemProgressBarProps) {
  return (
    <div className={styles['progressbar-container']}>
      <div className={styles.progressbar}>
        <Image
          src="/images/glu_character.png"
          alt="character"
          className={styles['progress-character']}
          style={{ left: `calc(${progressPercentage}%` }}
          width={80}
          height={110}
        />
      </div>
      <Image
        src="/images/problem/house.png"
        alt="house"
        className={styles['progress-house']}
        width={120}
        height={120}
      />
    </div>
  );
}
