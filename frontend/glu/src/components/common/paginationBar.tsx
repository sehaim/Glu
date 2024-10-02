/* eslint-disable react/button-has-type */
import styles from './paginationBar.module.css';

interface PaginationBarProps {
  pageNumbers: number[];
  currentPage: number;
  handleClick: (page: number) => void;
}

export default function PaginationBar({
  pageNumbers,
  currentPage,
  handleClick,
}: PaginationBarProps) {
  return (
    <div className={styles.container}>
      {pageNumbers?.map((idx) => (
        <button
          key={idx}
          className={`${styles['page-button']} ${idx === currentPage ? styles['page-button-active'] : ''}`}
          onClick={() => handleClick(idx)}
        >
          {idx}
        </button>
      ))}
    </div>
  );
}
