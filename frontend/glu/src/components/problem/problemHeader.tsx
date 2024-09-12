/* eslint-disable jsx-a11y/no-static-element-interactions */
import { useState } from 'react';
import { BsStar } from 'react-icons/bs';
import styles from './problemHeader.module.css';

interface ProblemHeaderProps {
  problemIndex?: number;
  problemLevel: string;
  problemType: string;
  problemTitle: string;
}

export default function ProblemHeader({
  problemIndex,
  problemLevel,
  problemType,
  problemTitle,
}: ProblemHeaderProps) {
  const [isPopupVisible, setIsPopupVisible] = useState(false);
  const [selectedMemoIndex, setSelectedMemoIndex] = useState<number | null>(
    null,
  );

  // Array of memo contents
  const memoContents = [
    'Memo 1 content',
    'Memo 2 content',
    'Memo 3 content',
    'Memo 4 content',
    'Memo 5 content',
    'Memo 6 content',
    'Memo 7 content',
    'Memo 8 content',
    'Memo 9 content',
    'Memo 10 content',
    'Memo 11 content',
  ];

  const handleMemoClick = (index: number) => {
    setSelectedMemoIndex(index);
    setIsPopupVisible(true);
  };

  const handleClosePopup = () => {
    setIsPopupVisible(false);
    setSelectedMemoIndex(null);
  };

  return (
    <div className={styles.container}>
      <div className={styles['status-container']}>
        <div className={styles['status-level-wrapper']}>
          <div className={styles['status-level']}>{problemLevel}</div>
          <div className={styles['memo-list']}>
            {memoContents.map((_, index) => (
              // eslint-disable-next-line jsx-a11y/click-events-have-key-events
              <div
                // eslint-disable-next-line react/no-array-index-key
                key={index}
                className={styles.memo}
                onClick={() => handleMemoClick(index)}
              />
            ))}
          </div>
        </div>
        <div className={styles['status-like-wrapper']}>
          <div className={styles['status-like-icon']}>
            <BsStar />
          </div>
        </div>
      </div>
      <div className={styles['info-container']}>
        <div className={styles['problem-type']}>
          <div className={styles['problem-type-text']}>{problemType}</div>
        </div>
        <div className={styles['problem-title']}>
          {problemIndex}
          {problemIndex && '.'} {problemTitle}
        </div>
      </div>

      {isPopupVisible && selectedMemoIndex !== null && (
        // eslint-disable-next-line jsx-a11y/click-events-have-key-events
        <div
          className={`${styles.popup} ${isPopupVisible ? styles['popup-active'] : ''}`}
          onClick={handleClosePopup}
        >
          <div className={styles['popup-content']}>
            <p>{memoContents[selectedMemoIndex]}</p>{' '}
            {/* Use the selected memo index to get the content */}
          </div>
        </div>
      )}
    </div>
  );
}
