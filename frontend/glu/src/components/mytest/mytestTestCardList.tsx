import { ProblemType, SolvedProblemResponse } from '@/types/ProblemTypes';
import { getSolvedTypeTestAPI } from '@/utils/user/mytest';
import { useState } from 'react';
import styles from './mytestTestCardList.module.css';
import TestCardItem from '../test/testCardItem';
import PaginationBar from '../common/paginationBar';
import MytestSortOptions from './mytestSortOption';

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
    const newPage = page - 1;

    if (page !== 1) {
      if (pageType === 'starred') {
        const data = await getSolvedTypeTestAPI(
          problemType.code,
          newPage,
          undefined,
          undefined,
          true,
          undefined,
          undefined,
        );
        setProblemList(data?.content);
      } else if (pageType === 'correct') {
        const data = await getSolvedTypeTestAPI(
          problemType.code,
          newPage,
          'CORRECT',
          undefined,
          undefined,
          undefined,
          undefined,
        );
        setProblemList(data?.content);
      } else if (pageType === 'incorrect') {
        const data = await getSolvedTypeTestAPI(
          problemType.code,
          newPage,
          'WRONG',
          undefined,
          undefined,
          undefined,
          undefined,
        );
        setProblemList(data?.content);
      }
    }
  };

  const [sortOption, setSortOption] = useState('createdDate,desc');

  const handleSortChange = async (value: string) => {
    setSortOption(value);

    const newPage = currentPage - 1;

    if (pageType === 'starred') {
      const data = await getSolvedTypeTestAPI(
        problemType.code,
        newPage,
        undefined,
        undefined,
        true,
        sortOption,
        undefined,
      );
      setProblemList(data?.content);
    } else if (pageType === 'correct') {
      const data = await getSolvedTypeTestAPI(
        problemType.code,
        newPage,
        'CORRECT',
        undefined,
        undefined,
        sortOption,
        undefined,
      );
      setProblemList(data?.content);
    } else if (pageType === 'incorrect') {
      const data = await getSolvedTypeTestAPI(
        problemType.code,
        newPage,
        'WRONG',
        undefined,
        undefined,
        sortOption,
        undefined,
      );
      setProblemList(data?.content);
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles['title-line']}>
        <div className={styles['type-name']}>{problemType.name}</div>
        <div className={styles['option-line']}>
          <MytestSortOptions onChange={handleSortChange} />
        </div>
      </div>
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
