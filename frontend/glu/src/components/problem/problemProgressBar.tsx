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
        <img
          src="/images/glu_character.png"
          alt="character"
          className={styles['progress-character']}
          style={{ left: `calc(${progressPercentage}%` }}
        />
      </div>
      <img
        src="/images/problem/house.png"
        alt="house"
        className={styles['progress-house']}
      />
    </div>
  );
}
