/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable react/jsx-no-comment-textnodes */
/* eslint-disable jsx-a11y/no-static-element-interactions */
import { BsStar, BsStarFill } from 'react-icons/bs';
import { useEffect, useState } from 'react';
import styles from './problemHeader.module.css';
import {
  postProblemLike as postProblemLikeAPI,
  deleteProblemLike as deleteProblemLikeAPI,
} from '../../utils/problem/like';

interface ProblemHeaderProps {
  problemId: number;
  problemIndex?: number;
  problemLevel: string;
  problemType: string;
  problemTitle: string;
  problemLike: boolean; // 초기 좋아요 상태
}

export default function ProblemHeader({
  problemId,
  problemIndex,
  problemLevel,
  problemType,
  problemTitle,
  problemLike, // 좋아요 초기 상태를 props로 받음
}: ProblemHeaderProps) {
  const [liked, setLiked] = useState<boolean>(false); // 좋아요 상태 관리

  useEffect(() => {
    setLiked(problemLike);
  }, []);

  const handleLikeClick = async () => {
    try {
      if (liked) {
        await deleteProblemLikeAPI(problemId); // 좋아요 취소 API 호출
      } else {
        await postProblemLikeAPI(problemId); // 좋아요 등록 API 호출
      }
      setLiked((prevLiked) => !prevLiked); // 좋아요 상태 토글
    } catch (error) {
      // TODO: 필요하다면 에러 처리 로직 추가 (예: 사용자에게 에러 메시지 표시)
      console.error('좋아요 처리 중 오류 발생:', error);
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles['status-container']}>
        <div className={styles['status-level-wrapper']}>
          <div className={styles['status-level']}>{problemLevel}</div>
        </div>
        <div className={styles['status-like-wrapper']}>
          <div className={styles['status-like-icon']} onClick={handleLikeClick}>
            {liked ? <BsStarFill /> : <BsStar />}
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
    </div>
  );
}
