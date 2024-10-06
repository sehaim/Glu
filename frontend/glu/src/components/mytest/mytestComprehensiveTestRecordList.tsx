import { useState } from 'react';
import { ComprehensiveTestRecord } from '@/types/TestTypes';
import Link from 'next/link';
import { getSolvedComprehensiveTestAPI } from '@/utils/user/mytest';
import { format } from 'date-fns';
import styles from './mytestComprehensiveTestRecordList.module.css';
import PaginationBar from '../common/paginationBar';

interface TestRecordListProps {
  initialTestList: ComprehensiveTestRecord[];
  totalPages: number;
}

export default function MytestComprehensiveTestRecordList({
  initialTestList,
  totalPages,
}: TestRecordListProps) {
  const [testList, setTestList] = useState(initialTestList);
  const [currentPage, setCurrentPage] = useState(1);

  const baseUrl = process.env.NEXT_PUBLIC_API_URL;

  const itemNameList = [
    '총 점수',
    '어휘 및 문법',
    '독해',
    '추론',
    '날짜',
    '걸린 시간',
  ];

  const handlePageClick = async (page: number) => {
    setCurrentPage(page);

    if (page !== 1) {
      const data = await getSolvedComprehensiveTestAPI(page, 5);
      setTestList(data?.content);
    }
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
        {testList.map((test) => (
          <Link
            href={`${baseUrl}/test/result/${test.testId}`}
            key={test.testId}
            className={`${styles['line-container']} ${styles['item-container']}`}
          >
            <div className={styles.element}>{test.totalCorrectCount}/15</div>
            <div className={styles.element}>3/5</div>
            <div className={styles.element}>4/5</div>
            <div className={styles.element}>3/5</div>
            <div className={styles.element}>
              {format(test.createdDate, 'yyyy-MM-dd')}
            </div>
            <div className={styles.element}>
              {Math.floor(test.totalSolvedTime / 60)}분{' '}
              {Math.floor(test.totalSolvedTime % 60)}초
            </div>
          </Link>
        ))}
        {initialTestList.length === 0 && (
          <div className={styles['test-record-null']}>
            종합 테스트 기록이 없습니다.
          </div>
        )}
      </div>
      <PaginationBar
        totalPageCount={totalPages}
        currentPage={currentPage}
        handleClick={handlePageClick}
      />
    </div>
  );
}
