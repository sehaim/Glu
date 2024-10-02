/* eslint-disable prettier/prettier */
import { useEffect, useState } from 'react';
import {
  ProblemLevel,
  ProblemOption,
  ProblemType,
  QuestionType,
} from '@/types/ProblemTypes';
import ProblemHeader from '@/components/problem/problemHeader';
import ProblemContentText from '@/components/problem/problemContentText';
import ProblemOptionList from '@/components/problem/problemOptionList';
import PrimaryButton from '@/components/common/buttons/primaryButton';
import ProblemMemoManager from '@/components/problem/problemMemoManager';
import { Memo } from '@/types/MemoTypes';
import {
  getSingleProblemAPI,
  postSingleProblemGradingAPI,
} from '@/utils/problem/problem';
// eslint-disable-next-line import/no-extraneous-dependencies
import throttle from 'lodash/throttle';
import { useRouter } from 'next/router';
import ProblemInputField from '@/components/problem/problemInputField';
import Image from 'next/image';
import LevelUpModal from '@/components/problem/result/levelUpModal';
import styles from './problem.module.css';

interface ProblemResponse {
  problemId: number;
  title: string;
  content: string;
  questionType: QuestionType;
  problemLevel: ProblemLevel;
  problemType: ProblemType;
  metadata: ProblemOption;
  solution: string;
  isFavorite: boolean;
  answer: string;
}

// export const getServerSideProps: GetServerSideProps = async (context) => {
//   const { Id } = context.query; // URL에서 problemId 가져오기

//   try {
//     // 단일 문제 API 호출
//     const problemData = await getSingleProblemAPI(context, Number(Id));

//     // problemData가 없으면 404 처리
//     if (!problemData) {
//       return {
//         notFound: true,
//       };
//     }

//     return {
//       props: {
//         problemData,
//       },
//     };
//   } catch (error) {
//     // 에러가 발생하면 더미 데이터를 반환
//     return {
//       props: {
//         problemData: dummyImageProblem,
//       },
//     };
//   }
// };

// interface TestProps {
//   problemData: ProblemResponse;
// }

export default function Test() {
  const [problem, setProblem] = useState<ProblemResponse | null>(null);
  const [answer, setAnswer] = useState<string>('');
  const [startTime, setStartTime] = useState<number>(Date.now());
  const [, setElapsedTime] = useState<number>(0);
  const [isMobile, setIsMobile] = useState(false); // 초기값 false로 설정
  const [isSolved, setIsSolved] = useState(false);
  const [isCorrect, setIsCorrect] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [stageImage, setStageImage] = useState('');
  const [memoList, setMemoList] = useState<Memo[]>([
    { memoId: 1, content: 'This is the first memo content.' },
    { memoId: 2, content: 'This is the second memo content.' },
    { memoId: 3, content: 'This is the third memo content.' },
    { memoId: 4, content: 'This is the fourth memo content.' },
  ]);

  const router = useRouter();
  const { id } = router.query;

  useEffect(() => {
    const fetchData = async () => {
      if (typeof id === 'string') {
        // id가 string인 경우에만 API 호출
        const res = await getSingleProblemAPI(id);
        console.log(res);
        setProblem(res.data);
      }
    };

    if (id) {
      fetchData();
    }
  }, [id]);

  useEffect(() => {
    const handleResize = throttle(() => {
      setIsMobile(window.innerWidth < 1024);
    }, 300); // 0.3초 간격으로 이벤트 처리

    if (typeof window !== 'undefined') {
      window.addEventListener('resize', handleResize);
      handleResize(); // 초기 사이즈 체크

      return () => {
        window.removeEventListener('resize', handleResize);
      };
    }

    return () => {};
  }, []);

  const handleAnswer = (userAnswer: string) => {
    setAnswer(userAnswer);
  };

  const handleSubmit = async () => {
    const endTime = Date.now(); // 제출 시점의 시간
    const timeTaken = Math.floor((endTime - startTime) / 1000); // 경과 시간 계산 (초 단위)
    setElapsedTime(timeTaken); // 경과 시간 상태 업데이트
    setStartTime(Date.now());

    if (problem) {
      try {
        // Call the postSingleProblemGrading function to submit the answer and time
        const response = await postSingleProblemGradingAPI(
          problem.problemId,
          answer,
          timeTaken,
        );
        console.log('단일 문제 채점 결과:', response);
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

  const handleMemoSave = (newMemo: Memo) => {
    setMemoList((prevMemoList) => {
      // 만약 기존 메모를 수정하는 경우 memoId를 비교하여 업데이트
      const memoIndex = prevMemoList.findIndex(
        (memo) => memo.memoId === newMemo.memoId,
      );
      if (memoIndex > -1) {
        // 기존 메모 수정
        const updatedMemoList = [...prevMemoList];
        updatedMemoList[memoIndex] = newMemo;
        return updatedMemoList;
      }
      // 새로운 메모 추가
      return [...prevMemoList, newMemo];
    });
  };

  return (
    <div className={styles.container}>
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
              {problem.questionType.code !== 'QT02' && (
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
          <div
            className={styles['problem-memo']}
            style={{
              position: isMobile ? 'static' : 'sticky',
              top: '60px',
              zIndex: 10,
            }}
          >
            <ProblemMemoManager
              memoList={memoList}
              onSaveMemo={handleMemoSave}
            />
          </div>
        </div>
      )}
    </div>
  );
}
