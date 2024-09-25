import React, { useEffect, useState } from 'react';
import styles from './barGraph.module.css';

interface BarGraphProps {
  maxScore: number;
  currentScore: number;
}

export default function ExperienceBar({
  maxScore,
  currentScore,
}: BarGraphProps) {
  const [score, setScore] = useState(0);

  // 애니메이션: 0에서 currentScore까지 증가
  useEffect(() => {
    let start = 0;
    const increment = currentScore / 40;
    const interval = setInterval(() => {
      start += increment;
      if (start >= currentScore) {
        start = currentScore;
        clearInterval(interval);
      }
      setScore(start);
    }, 10); // 10ms마다 업데이트

    return () => clearInterval(interval);
  }, [currentScore]);

  const progress = (score / maxScore) * 100;

  return (
    <div className={styles.bar}>
      <div className={styles.fill} style={{ width: `${progress}%` }}>
        <span className={styles.label}>{Math.round(progress)}%</span>
      </div>
    </div>
  );
}
