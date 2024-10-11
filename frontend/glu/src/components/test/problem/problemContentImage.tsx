import React from 'react';
import Image from 'next/image';
import styles from './problemContentImage.module.css';

interface ProblemContentImageProps {
  imageUrl: string;
  altText?: string;
}

function ProblemContentImage({
  imageUrl,
  altText = '이미지 설명',
}: ProblemContentImageProps) {
  return (
    <div className={styles['problem-image']}>
      <Image
        src={imageUrl}
        alt={altText}
        className={styles.image}
        width={420}
        height={420}
      />
    </div>
  );
}

export default React.memo(ProblemContentImage);
