import { ProblemType, SolvedProblemResponse } from '@/types/ProblemTypes';
import { getSolvedTypeTestAPI } from '@/utils/user/mytest';
import { useState } from 'react';
import styles from './mytestTestCardList.module.css';
import TestCardItem from '../test/testCardItem';
import PaginationBar from '../common/paginationBar';

interface MytestCardListProps {
  testData: SolvedProblemResponse;
  problemType: ProblemType;
  pageType: string;
}

export default function MytestTestCardList({
  testData,
  problemType,
  pageType,
}: MytestCardListProps) {
  const [problemList, setProblemList] = useState(testData?.content);
  const [currentPage, setCurrentPage] = useState(1);

  const handlePageClick = async (page: number) => {
    setCurrentPage(page);

    if (page !== 1) {
      if (pageType === 'starred') {
        const data = await getSolvedTypeTestAPI(
          problemType.code,
          currentPage - 1,
          undefined,
          undefined,
          true,
          undefined,
        );
        setProblemList(data?.content);
      } else if (pageType === 'correct') {
        const data = await getSolvedTypeTestAPI(
          problemType.code,
          currentPage - 1,
          'CORRECT',
          undefined,
          true,
          undefined,
        );
        setProblemList(data?.content);
      } else if (pageType === 'incorrect') {
        const data = await getSolvedTypeTestAPI(
          problemType.code,
          currentPage - 1,
          'WRONG',
          undefined,
          true,
          undefined,
        );
        setProblemList(data?.content);
      }
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles['type-name']}>{problemType.name}</div>
      <div className={styles['card-list-container']}>
        {problemList.map((problem) => (
          <TestCardItem key={problem.problemId} problem={problem} />
        ))}
        {problemList.length === 0 && (
          <div className={styles['test-record-null']}>
            문제가 존재하지 않습니다.
          </div>
        )}
      </div>
      <PaginationBar
        totalPageCount={testData.totalPages}
        currentPage={currentPage}
        handleClick={handlePageClick}
      />
    </div>
  );
}
