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

  const handlePageClick = (page: number) => {
    setCurrentPage(page);
  };

  return (
    <div className={styles['list-container']}>
      <div className={styles['section-name']}>나의 테스트 기록</div>
      <div className={styles['test-list']}>테스트 기록</div>
      <PaginationBar
        pageNumbers={Array.from({ length: 7 }, (_, i) => i + 1)}
        currentPage={currentPage}
        onClick={handlePageClick}
      />
    </div>
  );
}
