import React from 'react';
import styles from './problemContentImage.module.css';

interface ProblemContentImageProps {
  imageUrl: string;
  altText?: string; // altText는 선택적으로 받습니다.
}

export default function ProblemContentImage({
  imageUrl,
  altText = '이미지 설명',
}: ProblemContentImageProps) {
  return (
    <div className={styles['problem-image']}>
      <img src={imageUrl} alt={altText} className={styles.image} />
    </div>
  );
}
