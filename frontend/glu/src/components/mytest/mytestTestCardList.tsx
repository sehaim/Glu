import { ProblemType, SolvedProblemResponse } from '@/types/ProblemTypes';
import { getSolvedTypeTestAPI } from '@/utils/user/mytest';
import { useState, useEffect, useCallback } from 'react';
import styles from './mytestTestCardList.module.css';
import TestCardItem from '../test/test/testCardItem';
import PaginationBar from '../common/paginationBar';
import MytestSortOptions from './mytestSortOption';
import MytestMemoOption from './mytestMemoOption';

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
  const [totalPages, setTotalPages] = useState(testData?.totalPages);
  const [problemList, setProblemList] = useState(testData?.content);
  const [currentPage, setCurrentPage] = useState(1);
  const [sortOption, setSortOption] = useState('createdDate,desc');
  const [hasMemo, setHasMemo] = useState(false);

  // API 호출 로직을 하나로 통합
  const fetchProblems = useCallback(
    async (page = currentPage, sort = sortOption, memo = hasMemo) => {
      const newPage = page - 1;

      let status: string | undefined;
      let isFavorite: boolean | undefined;

      if (pageType === 'starred') {
        isFavorite = true;
      } else if (pageType === 'correct') {
        status = 'CORRECT';
      } else if (pageType === 'incorrect') {
        status = 'WRONG';
      }

      const data = await getSolvedTypeTestAPI(
        problemType.code,
        newPage,
        status,
        memo,
        isFavorite,
        sort,
        undefined,
      );
      if (data) {
        setProblemList(data.content);
        setTotalPages(data.totalPages);
      }
    },
    [problemType.code, currentPage, sortOption, hasMemo, pageType],
  );

  // 페이지 변경 시 문제 리스트 재조회
  const handlePageClick = (page: number) => {
    setCurrentPage(page);
  };

  // 정렬 옵션 변경 시 호출
  const handleSortChange = (value: string) => {
    setSortOption(value);
  };

  // 메모 필터링 옵션 변경 시 호출
  const handleMemoOptionChange = (isChecked: boolean) => {
    setHasMemo(isChecked);
  };

  // 상태가 변경될 때마다 문제 리스트를 재조회
  useEffect(() => {
    fetchProblems();
  }, [currentPage, sortOption, hasMemo, fetchProblems]);

  return (
    <div className={styles.container}>
      <div className={styles['title-line']}>
        <div className={styles['type-name']}>{problemType.name}</div>
        <div className={styles['option-line']}>
          <MytestMemoOption onChange={handleMemoOptionChange} />
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
        totalPageCount={totalPages}
        currentPage={currentPage}
        handleClick={handlePageClick}
      />
    </div>
  );
}
