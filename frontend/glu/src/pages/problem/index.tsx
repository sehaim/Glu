import { useState, useEffect } from 'react';
import dummyProblemList from '@/mock/dummyProblemList.json';
import { Problem } from '@/types/ProblemTypes';
import SecondaryButton from '@/components/common/buttons/secondaryButton';
import styles from './problemList.module.css';
import Link from 'next/link';

export default function ProblemList() {
  const [wordRulesTests, setWordRulesTests] = useState<Problem[]>([]);
  const [readingTests, setReadingTests] = useState<Problem[]>([]);
  const [inferenceTests, setInferenceTests] = useState<Problem[]>([]);

  const [wordRulesIndex, setWordRulesIndex] = useState(0);
  const [readingIndex, setReadingIndex] = useState(0);
  const [inferenceIndex, setInferenceIndex] = useState(0);

  const LIMIT = 5;

  const fetchTests = (category: string): Problem[] => {
    switch (category) {
      case 'wordRules': {
        return dummyProblemList;
        break;
      }
      case 'reading': {
        return dummyProblemList;
        break;
      }
      case 'inference': {
        return dummyProblemList;
        break;
      }
      default:
        break;
    }
    return dummyProblemList;
  };

  useEffect(() => {
    setWordRulesTests(fetchTests('wordRules'));
    setReadingTests(fetchTests('reading'));
    setInferenceTests(fetchTests('inference'));
  }, []);

  const handleNextCard = (category: string) => {
    switch (category) {
      case 'wordRules': {
        const newIndex = wordRulesIndex + 1;
        if (newIndex * LIMIT < wordRulesTests.length) {
          setWordRulesIndex(newIndex);
        }
        break;
      }
      case 'reading': {
        const newIndex = readingIndex + 1;
        if (newIndex * LIMIT < readingTests.length) {
          setReadingIndex(newIndex);
        }
        break;
      }
      case 'inference': {
        const newIndex = inferenceIndex + 1;
        if (newIndex * LIMIT < inferenceTests.length) {
          setInferenceIndex(newIndex);
        }
        break;
      }
      default:
        break;
    }
  };

  const handlePrevCard = (category: string) => {
    switch (category) {
      case 'wordRules': {
        const newIndex = wordRulesIndex - 1;
        if (newIndex >= 0) {
          setWordRulesIndex(newIndex);
        }
        break;
      }
      case 'reading': {
        const newIndex = readingIndex - 1;
        if (newIndex >= 0) {
          setReadingIndex(newIndex);
        }
        break;
      }
      case 'inference': {
        const newIndex = inferenceIndex - 1;
        if (newIndex >= 0) {
          setInferenceIndex(newIndex);
        }
        break;
      }
      default:
        break;
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles['content-wrapper']}>
        <div>
          <h2 className={styles['page-title']}>유형 테스트 추천</h2>
          <p>
            김싸피님을 위한 유형별 추천 문제입니다.
            <br />
            유형에 따른 현재 나의 레벨과 비슷한 문제들로 구성되어 있어요. 문제
            카드의 레벨을 확인하여 한문제 씩 자유롭게 골라 풀어보세요.
            <br />
          </p>
        </div>
      </div>

      {/* 단어와 문장규칙 */}
      <div className={styles['content-wrapper']}>
        <div>
          <h3 className={styles['page-subTitle']}>단어와 문장규칙</h3>
          <div className={styles['test-cards']}>
            {wordRulesTests
              .slice(wordRulesIndex * LIMIT, (wordRulesIndex + 1) * LIMIT)
              .map((test) => (
                <Link href={`/problem/${test.problemId}`} key={test.problemId}>
                  <div className={styles['test-card']}>
                    <span>{test.title}</span>
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
              onClick={() => handlePrevCard('wordRules')}
              disabled={wordRulesIndex === 0}
            />
            <SecondaryButton
              label="다음"
              size="small"
              onClick={() => handleNextCard('wordRules')}
              disabled={(wordRulesIndex + 1) * LIMIT >= wordRulesTests.length}
            />
          </div>
        </div>
      </div>

      {/* 글읽고이해하기 */}
      <div className={styles['content-wrapper']}>
        <div>
          <h3 className={styles['page-subTitle']}>글읽고 이해하기</h3>
          <div className={styles['test-cards']}>
            {readingTests
              .slice(readingIndex * LIMIT, (readingIndex + 1) * LIMIT)
              .map((test) => (
                <Link href={`/problem/${test.problemId}`} key={test.problemId}>
                  <div className={styles['test-card']}>
                    <span>{test.title}</span>
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
              onClick={() => handlePrevCard('reading')}
              disabled={readingIndex === 0}
            />
            <SecondaryButton
              label="다음"
              size="small"
              onClick={() => handleNextCard('reading')}
              disabled={(readingIndex + 1) * LIMIT >= readingTests.length}
              aria-label="Next Page"
            />
          </div>
        </div>
      </div>

      {/* 추측하기 */}
      <div className={styles['content-wrapper']}>
        <div>
          <h3 className={styles['page-subTitle']}>추측하기</h3>
          <div className={styles['test-cards']}>
            {inferenceTests
              .slice(inferenceIndex * LIMIT, (inferenceIndex + 1) * LIMIT)
              .map((test) => (
                <Link href={`/problem/${test.problemId}`} key={test.problemId}>
                  <div className={styles['test-card']}>
                    <span>{test.title}</span>
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
              onClick={() => handlePrevCard('inference')}
              disabled={inferenceIndex === 0}
            />
            <SecondaryButton
              label="다음"
              size="small"
              onClick={() => handleNextCard('inference')}
              disabled={(inferenceIndex + 1) * LIMIT >= inferenceTests.length}
              aria-label="Next Page"
            />
          </div>
        </div>
      </div>
    </div>
  );
}
