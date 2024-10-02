/* eslint-disable react/button-has-type */
import { BsCaretLeftFill, BsCaretRightFill } from 'react-icons/bs';
import styles from './paginationBar.module.css';

interface PaginationBarProps {
  totalPageCount: number;
  currentPage: number;
  handleClick: (page: number) => void;
}

export default function PaginationBar({
  totalPageCount,
  currentPage,
  handleClick,
}: PaginationBarProps) {
  const maxPageButtons = 5;

  const renderPageButtons = () => {
    const pageList = [];

    let startPage = Math.max(1, currentPage - Math.floor(maxPageButtons / 2));
    const endPage = Math.min(totalPageCount, startPage + maxPageButtons - 1);

    if (endPage - startPage < maxPageButtons - 1) {
      startPage = Math.max(1, endPage - maxPageButtons + 1);
    }

    for (let i = startPage; i <= endPage; i += 1) {
      pageList.push(
        <button
          key={i}
          className={`${styles['page-button']} ${
            i === currentPage ? styles['page-button-active'] : ''
          }`}
          onClick={() => handleClick(i)}
        >
          {i}
        </button>,
      );
    }

    return pageList;
  };

  return (
    <div className={styles.container}>
      <button
        className={`${styles['dir-button']} ${currentPage === 1 ? styles['dir-button-disabled'] : ''}`}
        onClick={() => handleClick(Math.max(1, currentPage - 1))}
        aria-label="Previous page"
      >
        <BsCaretLeftFill />
      </button>
      {renderPageButtons()}
      <button
        className={`${styles['dir-button']} ${currentPage === totalPageCount ? styles['dir-button-disabled'] : ''}`}
        onClick={() => handleClick(Math.min(totalPageCount, currentPage + 1))}
        aria-label="Next page"
      >
        <BsCaretRightFill />
      </button>
    </div>
  );
}
