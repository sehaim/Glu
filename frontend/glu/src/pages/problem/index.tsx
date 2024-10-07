import { useState } from 'react';
import { Problem } from '@/types/ProblemTypes';
import SecondaryButton from '@/components/common/buttons/secondaryButton';
import Link from 'next/link';
import { getRecommendedTypeProblemsAPI } from '@/utils/problem/problem';
import { useSelector } from 'react-redux';
import { RootState } from '@/store';
import { GetServerSideProps, GetServerSidePropsContext } from 'next';
import styles from './problemList.module.css';

export const getServerSideProps: GetServerSideProps = async (
  context: GetServerSidePropsContext,
) => {
  const res = await getRecommendedTypeProblemsAPI(context);
  const initialProblems = res.data;

  return {
    props: {
      initialProblems,
    },
  };
};

interface ProblemListProps {
  initialProblems: Problem[];
}

export default function ProblemList({ initialProblems }: ProblemListProps) {
  const username = useSelector((state: RootState) => state.auth.nickname);
  const [problems] = useState<Problem[]>(initialProblems);
  const [problemIndex, setProblemIndex] = useState(0);
  const LIMIT = 10;

  const handleNextCard = () => {
    const newIndex = problemIndex + 1;
    if (newIndex * LIMIT < problems.length) setProblemIndex(newIndex);
  };

  const handlePrevCard = () => {
    const newIndex = problemIndex - 1;
    if (newIndex >= 0) setProblemIndex(newIndex);
  };

  return (
    <div className={styles.container}>
      <div className={styles['content-wrapper']}>
        <div>
          <h2 className={styles['page-title']}>유형 테스트 추천</h2>
          <p>
            <span id={styles.username}>{username}</span>님을 위한 유형별 추천
            문제입니다.
            <br />
            유형에 따른 현재 나의 레벨과 비슷한 문제들로 구성되어 있어요. 문제
            카드의 레벨을 확인하여 한문제 씩 자유롭게 골라 풀어보세요.
            <br />
          </p>
        </div>
      </div>

      <div className={styles['content-wrapper']}>
        <div className={styles['test-category']}>
          <h3 className={styles['page-subTitle']}>단어와 문장규칙</h3>
          <div className={styles['test-cards']}>
            {problems
              .slice(problemIndex * LIMIT, (problemIndex + 1) * LIMIT)
              .map((test) => (
                <Link href={`/problem/${test.problemId}`} key={test.problemId}>
                  <div className={styles['test-card']}>
                    <span>
                      {test.title.length > 8
                        ? `${test.title.slice(0, 8)}..`
                        : test.title}
                    </span>

                    <span className={styles['level-span']}>
                      {test.problemLevel.name}
                    </span>
                  </div>
                </Link>
              ))}
          </div>
          <div className={styles.pagination}>
            <SecondaryButton
              label="이전"
              size="small"
              onClick={handlePrevCard}
              disabled={problemIndex === 0}
            />
            <SecondaryButton
              label="다음"
              size="small"
              onClick={handleNextCard}
              disabled={(problemIndex + 1) * LIMIT >= problems.length}
            />
          </div>
        </div>
      </div>
    </div>
  );
}
