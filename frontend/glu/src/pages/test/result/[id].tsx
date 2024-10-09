import { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Image from 'next/image';
import { SolvedProblem, SolvedProblemType } from '@/types/ProblemTypes';
import RadarGraph from '@/components/common/graphs/radarGraph';
import { formatTime } from '@/utils/problem/result';
import { RootState } from '@/store';
import { resetLevel } from '@/store/levelupSlice';
import { FaCheck, FaTimes } from 'react-icons/fa';
import Loading from '@/components/common/loading';
import { getTestResultAPI, getTestResultCSRAPI } from '@/utils/problem/test';
import { GetServerSideProps } from 'next';
import Head from 'next/head';
import LevelUpModal from '@/components/test/result/levelUpModal';
import { useRouter } from 'next/router';
import styles from './testResult.module.css';

interface TestResultResponse {
  totalCorrectCount: number;
  totalSolvedTime: number;
  problemTypeList: SolvedProblemType[];
  problemList: SolvedProblem[];
}

export const getServerSideProps: GetServerSideProps = async (context) => {
  const { id } = context.query;

  if (!id) {
    return { notFound: true };
  }

  const testId = Array.isArray(id) ? id[0] : id;

  try {
    const response = await getTestResultAPI(context, testId);

    return {
      props: {
        testResultResponse: {
          totalCorrectCount: response.data.totalCorrectCount,
          totalSolvedTime: response.data.totalSolvedTime,
          problemTypeList: response.data.gradingResultByTypeList,
          problemList: response.data.gradingResultByProblemList,
        },
      },
    };
  } catch (error) {
    return { notFound: true };
  }
};

interface TestResultProps {
  testResultResponse: TestResultResponse;
}

export default function TestResult({ testResultResponse }: TestResultProps) {
  const router = useRouter();
  const { id } = router.query;
  const dispatch = useDispatch();
  const [totalCorrectCount, setTotalCorrectCount] = useState(
    testResultResponse.totalCorrectCount,
  );
  const [totalSolvedTime, setTotalSolvedTime] = useState(
    testResultResponse.totalSolvedTime,
  );
  const [problemTypeList, setProblemTypeList] = useState(
    testResultResponse.problemTypeList,
  );
  const [problemList, setProblemList] = useState(
    testResultResponse.problemList,
  );
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const { levelImage, isLeveledUp } = useSelector(
    (state: RootState) => state.levelup,
  );

  useEffect(() => {
    if (problemList.length !== 15) {
      const fetchAgain = async () => {
        if (typeof id === 'string') {
          try {
            const res = await getTestResultCSRAPI(id);

            setTotalCorrectCount(res.data.totalCorrectCount);
            setTotalSolvedTime(res.data.totalSolvedTime);
            setProblemList(res.data.gradingResultByProblemList);
            setProblemTypeList(res.data.gradingResultByTypeList);
          } catch (error) {
            console.error('데이터를 다시 가져오는 중 오류 발생:', error);
          }
        }
      };

      fetchAgain(); // 함수 호출
    }
  }, [problemList]);

  const data = problemTypeList.map((item) => ({
    axis: item.problemType.name,
    value: item.correctCount,
  }));

  useEffect(() => {
    // 레벨업이 되었을 때 모달을 열기
    if (isLeveledUp) {
      setIsModalOpen(true);
    }
  }, [isLeveledUp]);

  const handleLevelUpModalClose = () => {
    setIsModalOpen(false);
    dispatch(resetLevel());
  };

  const renderProblemRows = (startIndex: number, endIndex: number) => (
    <>
      <tr>
        {problemList
          ?.slice(startIndex, endIndex)
          .map((problem, index) => (
            <td key={problem.problemId + String(startIndex) + String(index)}>
              {startIndex + index + 1}
            </td>
          ))}
      </tr>
      <tr>
        {problemList.slice(startIndex, endIndex).map((problem, index) => (
          <td key={problem.problemId + String(startIndex) + String(index)}>
            {problem.isCorrect ? (
              <FaCheck className={styles.solve} />
            ) : (
              <FaTimes className={styles['solve-not']} />
            )}
          </td>
        ))}
      </tr>
    </>
  );

  if (problemList.length !== 15) {
    return (
      <div className={styles.container}>
        <Loading size="large" showText />
      </div>
    );
  }

  return (
    <div className={styles.container}>
      <Head>
        <title>테스트 결과</title>
        <meta
          property="og:title"
          content={`테스트 결과 - 총점: ${totalCorrectCount}/15`}
        />
        <meta
          property="og:description"
          content="테스트 결과와 문제 해설을 확인하세요."
        />
        <meta property="og:type" content="website" />
      </Head>

      {/* 레벨업 모달 */}
      <LevelUpModal show={isModalOpen} onClose={handleLevelUpModalClose}>
        <div className={styles.levelUp}>
          {/* <h2 className={styles['levelUp-title']}>LV. {level}</h2> */}
          <Image
            className={styles['levelUp-image']}
            src={levelImage}
            alt="레벨업 이미지"
            width={300}
            height={356}
          />
        </div>
      </LevelUpModal>

      {/* 테스트 결과 해설 */}
      <h2 className={styles['page-title']}>테스트 결과</h2>
      <div className={styles['result-container']}>
        <div className={styles['result-wrapper']}>
          <div className={styles['result-item-row']}>
            <p className={styles['item-title']}>내 점수</p>
            <div
              className={`${styles['item-content']} ${styles['item-content-text']}`}
            >
              {totalCorrectCount}/15
            </div>
          </div>
          <div className={styles['result-item-row']}>
            <p className={styles['item-title']}>걸린 시간</p>
            <p
              className={`${styles['item-content']} ${styles['item-content-text']}`}
            >
              {formatTime(totalSolvedTime)}
            </p>
          </div>
          <div className={styles['result-item']}>
            <p className={styles['item-title']}>맞힌 문제</p>
            <div className={styles['item-content']}>
              <table
                className={`${styles['item-content']} ${styles['result-table']}`}
              >
                <tbody>
                  {renderProblemRows(0, 5)}
                  {renderProblemRows(5, 10)}
                  {renderProblemRows(10, 15)}
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div className={styles['result-wrapper']}>
          <div className={styles['result-item']}>
            <p className={styles['item-title']}>영역별 점수</p>
            <div className={styles.canvasWrapper}>
              {/* RadarChart 컴포넌트 */}
              <RadarGraph data={data} maxScore={5} />
            </div>
          </div>
        </div>
      </div>

      {/* 문제 해설 */}
      <div className={styles['solution-container']}>
        <p className={styles['item-title']}>문제 해설</p>
        <div className={styles['solution-list']}>
          {problemList.map((problem, index) => (
            <div
              key={problem.problemId + String(index)}
              className={styles['solution-item']}
            >
              <div className={styles['solution-title']}>
                <p className={styles['solution-type']}>
                  {problem.problemType.name}
                </p>
                {index + 1}. {problem.title}
              </div>
              <div className={styles['solution-problem']}>
                <div className={styles['problem-content']}>
                  {problem.content}
                </div>
                <div className={styles['problem-option-list']}>
                  {problem.problemTypeDetail.code === 'PT0311'
                    ? (Array.isArray(problem.metadata.options)
                        ? problem.metadata.options
                        : [problem.metadata.options]
                      ) // 문자열일 경우 배열로 변환
                        .map((imageSrc: string, imageIndex: number) => (
                          <div
                            key={imageSrc}
                            className={styles['problem-image-item']}
                          >
                            <Image
                              key={imageSrc}
                              src={imageSrc}
                              alt={`Option ${imageIndex + 1}`}
                              width={100}
                              height={100}
                              className={styles['problem-image']}
                            />
                          </div>
                        ))
                    : (Array.isArray(problem.metadata.options)
                        ? problem.metadata.options
                        : [problem.metadata.options]
                      ) // 문자열일 경우 배열로 변환
                        .map((problemOption: string, optionIndex: number) => (
                          <p
                            key={problemOption}
                            className={styles['problem-option-item']}
                          >
                            {optionIndex + 1}. {problemOption}{' '}
                          </p>
                        ))}
                </div>
              </div>
              <div className={styles['problem-solution']}>
                {problem.isCorrect && (
                  <p className={styles['correct-message']}>
                    {problem.userAnswer}
                  </p>
                )}
                {!problem.isCorrect && (
                  <p className={styles['incorrect-message']}>
                    {problem.userAnswer}
                  </p>
                )}
                <div className={styles['solution-content']}>
                  {problem.solution}
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
