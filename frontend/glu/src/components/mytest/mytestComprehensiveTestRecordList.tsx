import { useState } from 'react';
import styles from './mytestComprehensiveTestRecordList.module.css';
import PaginationBar from '../common/paginationBar';

export default function MytestComprehensiveTestRecordList() {
  const [currentPage, setCurrentPage] = useState(1);
  const pageSize = 5; // 한 페이지에 보여줄 테스트 기록 수

  // const totalPages = Math.ceil(testRecords.length / pageSize);

  // // 현재 페이지에 보여줄 테스트 기록들
  // const currentRecords = testRecords.slice(
  //   (currentPage - 1) * pageSize,
  //   currentPage * pageSize,
  // );

  const itemNameList = [
    '내 점수',
    '어휘 및 문법',
    '독해',
    '추론',
    '날짜',
    '걸린 시간',
  ];

  const handlePageClick = (page: number) => {
    setCurrentPage(page);
  };

  return (
    <div className={styles.container}>
      <div className={styles['section-name']}>나의 테스트 기록</div>
      <div className={styles['record-container']}>
        <div className={styles['line-container']}>
          {itemNameList.map((itemName) => (
            <div key={itemName} className={styles['element-name']}>
              {itemName}
            </div>
          ))}
        </div>
        <div
          className={`${styles['line-container']} ${styles['item-container']}`}
        >
          <div className={styles.element}>10/15</div>
          <div className={styles.element}>3/5</div>
          <div className={styles.element}>4/5</div>
          <div className={styles.element}>3/5</div>
          <div className={styles.element}>2024-08-20</div>
          <div className={styles.element}>12분 10초</div>
        </div>
        <div
          className={`${styles['line-container']} ${styles['item-container']}`}
        >
          <div className={styles.element}>10/15</div>
          <div className={styles.element}>3/5</div>
          <div className={styles.element}>4/5</div>
          <div className={styles.element}>3/5</div>
          <div className={styles.element}>2024-08-20</div>
          <div className={styles.element}>12분 10초</div>
        </div>
        <div
          className={`${styles['line-container']} ${styles['item-container']}`}
        >
          <div className={styles.element}>10/15</div>
          <div className={styles.element}>3/5</div>
          <div className={styles.element}>4/5</div>
          <div className={styles.element}>3/5</div>
          <div className={styles.element}>2024-08-20</div>
          <div className={styles.element}>12분 10초</div>
        </div>
        <div
          className={`${styles['line-container']} ${styles['item-container']}`}
        >
          <div className={styles.element}>10/15</div>
          <div className={styles.element}>3/5</div>
          <div className={styles.element}>4/5</div>
          <div className={styles.element}>3/5</div>
          <div className={styles.element}>2024-08-20</div>
          <div className={styles.element}>12분 10초</div>
        </div>
        <div
          className={`${styles['line-container']} ${styles['item-container']}`}
        >
          <div className={styles.element}>10/15</div>
          <div className={styles.element}>3/5</div>
          <div className={styles.element}>4/5</div>
          <div className={styles.element}>3/5</div>
          <div className={styles.element}>2024-08-20</div>
          <div className={styles.element}>12분 10초</div>
        </div>
      </div>
      <PaginationBar
        pageNumbers={Array.from({ length: 7 }, (_, i) => i + 1)}
        currentPage={currentPage}
        handleClick={handlePageClick}
      />
    </div>
  );
}
