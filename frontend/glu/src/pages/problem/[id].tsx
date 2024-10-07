/* eslint-disable prettier/prettier */
import { useEffect, useState } from 'react';
import {
  ProblemLevel,
  ProblemOption,
  ProblemType,
  ProblemTypeDetail,
  QuestionType,
} from '@/types/ProblemTypes';
import ProblemHeader from '@/components/problem/problemHeader';
import ProblemContentText from '@/components/problem/problemContentText';
import ProblemOptionList from '@/components/problem/problemOptionList';
import PrimaryButton from '@/components/common/buttons/primaryButton';
import ProblemMemoManager from '@/components/problem/problemMemoManager';
import {
  getSingleProblemAPI,
  postSingleProblemGradingAPI,
} from '@/utils/problem/problem';
import { useRouter } from 'next/router';
import ProblemInputField from '@/components/problem/problemInputField';
import Image from 'next/image';
import LevelUpModal from '@/components/problem/result/levelUpModal';
import ProblemImageOptionList from '@/components/problem/problemImageOptionList';
import Head from 'next/head';
import styles from './problem.module.css';

interface ProblemResponse {
  problemId: string;
  title: string;
  content: string;
  questionType: QuestionType;
  problemLevel: ProblemLevel;
  problemType: ProblemType;
  problemTypeDetail: ProblemTypeDetail;
  metadata: ProblemOption;
  solution: string;
  isFavorite: boolean;
  answer: string;
}

export default function Test() {
  const router = useRouter();
  const { id } = router.query;
  const [problem, setProblem] = useState<ProblemResponse | null>(null);
  const [answer, setAnswer] = useState<string>('');
  const [startTime, setStartTime] = useState<number>(Date.now());
  const [, setElapsedTime] = useState<number>(0);
  const [isSolved, setIsSolved] = useState(false);
  const [isCorrect, setIsCorrect] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [stageImage, setStageImage] = useState('');

  useEffect(() => {
    const fetchData = async () => {
      if (typeof id === 'string') {
        // id가 string인 경우에만 API 호출
        const res = await getSingleProblemAPI(id);

        setProblem(res.data);
      }
    };

    if (id) {
      fetchData();
    }
  }, [id]);

  const handleAnswer = (userAnswer: string) => {
    if (isSolved) return;
    setAnswer(userAnswer);
  };

  const handleSubmit = async () => {
    const endTime = Date.now(); // 제출 시점의 시간
    const timeTaken = Math.floor((endTime - startTime) / 1000); // 경과 시간 계산 (초 단위)
    setElapsedTime(timeTaken); // 경과 시간 상태 업데이트
    setStartTime(Date.now());

    if (problem) {
      try {
        const response = await postSingleProblemGradingAPI(
          problem.problemId,
          answer,
          timeTaken,
        );

        setIsSolved(true);
        setIsCorrect(response.data.isCorrect);
        if (response.data.isStageUp) {
          setIsModalOpen(true);
          setStageImage(response.data.stageUpUrl);
        }
      } catch (error) {
        console.error('단일 문제 채점 중 에러 발생:', error);
      }
    }
  };

  const handleLevelUpModalClose = () => {
    setIsModalOpen(false);
  };

  return (
    <div className={styles.container}>
      <Head>
        <title>Glu 유형 문제</title>
        <meta
          property="og:title"
          content={`Glu 문제: ${problem?.problemLevel?.name}-${problem?.problemType?.name}-${problem?.problemId?.slice(-3)}`}
        />
        <meta
          property="og:description"
          content={problem?.content?.substring(0, 100)}
        />
        <meta property="og:type" content="website" />
      </Head>

      {/* 레벨업 모달 */}
      <LevelUpModal show={isModalOpen} onClose={handleLevelUpModalClose}>
        <div className={styles.levelUp}>
          <Image
            className={styles['levelUp-image']}
            src={stageImage}
            alt="레벨업 이미지"
            width={300}
            height={356}
          />
        </div>
      </LevelUpModal>

      {problem && (
        <div className={styles['problem-container']}>
          <div className={styles.problem}>
            <ProblemHeader
              problemLevel={problem.problemLevel.name}
              problemType={problem.problemType.name}
              problemTitle={problem.title}
              problemId={problem.problemId.toString()}
              problemLike={problem.isFavorite}
            />
            <div className={styles['problem-content']}>
              <ProblemContentText problemContent={problem.content} />
              {problem.problemTypeDetail.code === 'PT0311' && (
                <ProblemImageOptionList
                  problemOptions={
                    Array.isArray(problem.metadata.options)
                      ? problem.metadata.options // string[]일 경우
                      : [problem.metadata.options] // string일 경우 배열로 변환
                  }
                  selectedOption={answer}
                  onSingleProblemAnswer={handleAnswer}
                />
              )}
              {problem.questionType.code === 'QT02' && (
                <ProblemInputField
                  placeholder={
                    Array.isArray(problem.metadata.options)
                      ? problem.metadata.options.join(', ') // string[]일 경우, 문자열로 변환 (쉼표로 연결된 문자열)
                      : problem.metadata.options // string일 경우 그대로 사용
                  }
                  onSingleProblemAnswer={handleAnswer}
                />
              )}
              {problem.problemTypeDetail.code !== 'PT0311' &&
                problem.questionType.code !== 'QT02' && (
                  <ProblemOptionList
                    problemOptions={
                      Array.isArray(problem.metadata.options)
                        ? problem.metadata.options // string[]일 경우
                        : [problem.metadata.options] // string일 경우 배열로 변환
                    }
                    selectedOption={answer}
                    onSingleProblemAnswer={handleAnswer}
                  />
                )}
            </div>
            {!isSolved && (
              <div className={styles['problem-button-list']}>
                <div />
                <PrimaryButton
                  size="small"
                  label="제출하기"
                  onClick={handleSubmit}
                />
              </div>
            )}
            {isSolved && (
              <div className={styles['problem-solution']}>
                {isCorrect && (
                  <>
                    <div className={styles['problem-solution-correct']}>
                      <p className={styles['problem-answer']}>
                        {problem.answer}
                      </p>
                      맞았습니다!
                    </div>{' '}
                    {problem?.solution}
                  </>
                )}
                {!isCorrect && (
                  <>
                    <div className={styles['problem-solution-incorret']}>
                      <p className={styles['problem-answer']}>
                        {problem.answer}
                      </p>
                      틀렸습니다ㅠㅠ
                    </div>{' '}
                    {problem?.solution}
                  </>
                )}
              </div>
            )}
          </div>
          <div className={styles['problem-memo']}>
            <ProblemMemoManager problemId={problem.problemId} />
          </div>
        </div>
      )}
    </div>
  );
}
