import { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Image from 'next/image';
import { SolvedProblem, SolvedProblemType } from '@/types/ProblemTypes';
import RadarChart from '@/components/problem/result/radarChart';
import { formatTime } from '@/utils/problem/result';
import { RootState } from '@/store';
import LevelUpModal from '@/components/problem/result/levelUpModal';
import { resetLevel } from '@/store/levelupSlice';
import { FaCheck, FaTimes } from 'react-icons/fa';
import Loading from '@/components/common/loading';
import { getTestResultAPI } from '@/utils/problem/test';
import { GetServerSideProps } from 'next';
import Head from 'next/head';
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
    // TODO: 에러 확인할 필요
    // console.log('Full response:', response.data);
    // console.log('totalCorrectCount:', response.data.totalCorrectCount);
    // console.log('totalSolvedTime:', response.data.totalSolvedTime);
    // console.log(
    //   'problemTypeList:',
    //   response.data.gradingResultByTypeList.length,
    // );
    // console.log(
    //   'problemList:',
    //   response.data.gradingResultByProblemList.length,
    // );

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
  const dispatch = useDispatch();
  const { totalCorrectCount, totalSolvedTime, problemTypeList, problemList } =
    testResultResponse;
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const { levelImage, isLeveledUp } = useSelector(
    (state: RootState) => state.levelup,
  );

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
              <RadarChart problemTypeList={problemTypeList} />
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
              <p className={styles['solution-title']}>
                {index + 1}. {problem.title}
              </p>
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
                            className={`${styles['problem-option-item']} ${
                              Number(problem.userAnswer) === optionIndex + 1
                                ? styles['user-answer']
                                : ''
                            }`}
                          >
                            {optionIndex + 1}. {problemOption}{' '}
                          </p>
                        ))}
                </div>
              </div>
              <div className={styles['problem-solution']}>
                {!problem.isCorrect && (
                  <p className={styles['incorrect-message']}>
                    틀린 문제입니다. 해설을 확인하세요.
                  </p>
                )}
                {problem.solution}
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
