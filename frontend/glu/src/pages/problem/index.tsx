import { useMemo, useState } from 'react';
import { Problem } from '@/types/ProblemTypes';
import SecondaryButton from '@/components/common/buttons/secondaryButton';
import { getRecommendedTypeProblemsAPI } from '@/utils/problem/problem';
import { useSelector } from 'react-redux';
import { RootState } from '@/store';
import { GetServerSideProps, GetServerSidePropsContext } from 'next';
import Head from 'next/head';
import TestCardItem from '@/components/test/test/testCardItem';
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
  const LIMIT = 5;

  const vocabularyProblems = useMemo(
    () =>
      initialProblems.filter((problem) => problem.problemType.code === 'PT01'),
    [initialProblems],
  );

  const readingProblems = useMemo(
    () =>
      initialProblems.filter((problem) => problem.problemType.code === 'PT02'),
    [initialProblems],
  );

  const inferenceProblems = useMemo(
    () =>
      initialProblems.filter((problem) => problem.problemType.code === 'PT03'),
    [initialProblems],
  );

  const [vocabIndex, setVocabIndex] = useState(0);
  const [readingIndex, setReadingIndex] = useState(0);
  const [inferenceIndex, setInferenceIndex] = useState(0);

  // 개별 페이지네이션 함수
  const handleNextVocab = () => {
    const newIndex = vocabIndex + 1;
    if (newIndex * LIMIT < vocabularyProblems.length) setVocabIndex(newIndex);
  };

  const handlePrevVocab = () => {
    const newIndex = vocabIndex - 1;
    if (newIndex >= 0) setVocabIndex(newIndex);
  };

  const handleNextReading = () => {
    const newIndex = readingIndex + 1;
    if (newIndex * LIMIT < readingProblems.length) setReadingIndex(newIndex);
  };

  const handlePrevReading = () => {
    const newIndex = readingIndex - 1;
    if (newIndex >= 0) setReadingIndex(newIndex);
  };

  const handleNextInference = () => {
    const newIndex = inferenceIndex + 1;
    if (newIndex * LIMIT < inferenceProblems.length)
      setInferenceIndex(newIndex);
  };

  const handlePrevInference = () => {
    const newIndex = inferenceIndex - 1;
    if (newIndex >= 0) setInferenceIndex(newIndex);
  };

  return (
    <div className={styles.container}>
      <Head>
        <title>유형 테스트 추천</title>
        <meta
          property="og:description"
          content="총 15문제로, 모든 유형이 포함되어 나의 문해력을 종합적으로 평가합니다."
        />
        <meta property="og:type" content="website" />
      </Head>

      <div className={styles['content-wrapper']}>
        <div>
          <h2 className={styles['page-title']}>유형 테스트 추천</h2>
          <p className={styles['page-text']}>
            <span id={styles.username}>{username}</span>님을 위한 유형별 추천
            문제입니다.
            <br />
            유형에 따른 현재 나의 레벨과 비슷한 문제들로 구성되어 있어요. 문제
            카드의 레벨을 확인하여 한문제 씩 자유롭게 골라 풀어보세요.
            <br />
          </p>
        </div>
      </div>

      <div className={styles['content-wrapper-column']}>
        {/* 어휘 및 문법 */}
        <div className={styles['test-category']}>
          <h3 className={styles['page-subTitle']}>어휘 및 문법</h3>
          <div className={styles['test-cards']}>
            {vocabularyProblems
              .slice(vocabIndex * LIMIT, (vocabIndex + 1) * LIMIT)
              .map((problem) => (
                <TestCardItem key={problem.problemId} problem={problem} />
              ))}
          </div>
          <div className={styles.pagination}>
            <SecondaryButton
              label="이전"
              size="small"
              onClick={handlePrevVocab}
              disabled={vocabIndex === 0}
            />
            <SecondaryButton
              label="다음"
              size="small"
              onClick={handleNextVocab}
              disabled={(vocabIndex + 1) * LIMIT >= vocabularyProblems.length}
            />
          </div>
        </div>

        {/* 독해 */}
        <div className={styles['test-category']}>
          <h3 className={styles['page-subTitle']}>독해</h3>
          <div className={styles['test-cards']}>
            {readingProblems
              .slice(readingIndex * LIMIT, (readingIndex + 1) * LIMIT)
              .map((problem) => (
                <TestCardItem key={problem.problemId} problem={problem} />
              ))}
          </div>
          <div className={styles.pagination}>
            <SecondaryButton
              label="이전"
              size="small"
              onClick={handlePrevReading}
              disabled={readingIndex === 0}
            />
            <SecondaryButton
              label="다음"
              size="small"
              onClick={handleNextReading}
              disabled={(readingIndex + 1) * LIMIT >= readingProblems.length}
            />
          </div>
        </div>

        {/* 추론 */}
        <div className={styles['test-category']}>
          <h3 className={styles['page-subTitle']}>추론</h3>
          <div className={styles['test-cards']}>
            {inferenceProblems
              .slice(inferenceIndex * LIMIT, (inferenceIndex + 1) * LIMIT)
              .map((problem) => (
                <TestCardItem key={problem.problemId} problem={problem} />
              ))}
          </div>
          <div className={styles.pagination}>
            <SecondaryButton
              label="이전"
              size="small"
              onClick={handlePrevInference}
              disabled={inferenceIndex === 0}
            />
            <SecondaryButton
              label="다음"
              size="small"
              onClick={handleNextInference}
              disabled={
                (inferenceIndex + 1) * LIMIT >= inferenceProblems.length
              }
            />
          </div>
        </div>
      </div>
    </div>
  );
}
