/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable react/jsx-no-comment-textnodes */
/* eslint-disable jsx-a11y/no-static-element-interactions */
import { BsStar, BsStarFill } from 'react-icons/bs';
import Image from 'next/image';
import { useEffect, useState } from 'react';
import styles from './problemHeader.module.css';
import {
  getProblemLikeAPI,
  postProblemLikeAPI,
  deleteProblemLikeAPI,
} from '../../../utils/problem/like';

interface ProblemHeaderProps {
  problemId: string;
  problemIndex?: number;
  problemLevel: string;
  problemType: string;
  problemTitle: string;
  problemLike?: boolean; // 초기 좋아요 상태
}

export default function ProblemHeader({
  problemId,
  problemIndex,
  problemLevel,
  problemType,
  problemTitle,
  problemLike,
}: ProblemHeaderProps) {
  const [liked, setLiked] = useState<boolean | undefined | null>(problemLike); // 좋아요 상태 관리

  useEffect(() => {
    // 문제 ID가 존재하는 경우에만 좋아요 상태를 가져옴
    if (!problemLike) {
      const fetchLikeStatus = async () => {
        try {
          const res = await getProblemLikeAPI(problemId); // API로 좋아요 상태 가져오기
          setLiked(res.data);
        } catch (error) {
          console.error('좋아요 상태 가져오기 실패:', error);
        }
      };

      fetchLikeStatus();
    } else {
      setLiked(problemLike); // props로 받은 초기 좋아요 상태 반영
    }
  }, [problemId, problemLike]);

  const handleLikeClick = async () => {
    try {
      if (liked) {
        await deleteProblemLikeAPI(problemId); // 좋아요 취소 API 호출
      } else {
        await postProblemLikeAPI(problemId); // 좋아요 등록 API 호출
      }
      setLiked((prevLiked) => !prevLiked); // 좋아요 상태 토글
    } catch (error) {
      // 오류 발생
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles['status-container']}>
        <div className={styles['status-level-wrapper']}>
          <div className={styles['status-level']}>
            LV.{problemLevel.slice(-1)}
          </div>
        </div>
        <div className={styles['status-like-wrapper']}>
          <div className={styles['status-like-icon']} onClick={handleLikeClick}>
            {liked ? (
              <BsStarFill className={styles['status-like']} />
            ) : (
              <BsStar />
            )}
          </div>
        </div>
      </div>
      <div className={styles['info-container']}>
        <div className={styles['problem-type']}>
          <Image
            src="/images/problem/cloud.png"
            alt="Problem Type"
            width={160}
            height={110}
            priority // 중요한 이미지일 경우 빠르게 로드
            className={styles['problem-type-image']}
          />
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
