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
import { useRouter } from 'next/router';
import styles from './testResult.module.css';

// interface ApiResponse {
//   totalCorrectCount: number;
//   totalSolvedTime: number; // 초 단위
//   gradingResultByTypeList: SolvedProblemType[];
//   gradingResultByProblemList: SolvedProblem[];
// }

export default function TestResult() {
  const dispatch = useDispatch();
  const router = useRouter();
  const { id } = router.query;
  const [loading, setLoading] = useState<boolean>(true); // 로딩 상태 추가
  const [totalCorrectCount, setTotalCorrectCount] = useState<number | null>(
    null,
  );
  const [totalSolvedTime, setTotalSolvedTime] = useState<number | null>(null);
  const [problemTypeList, setProblemTypeList] = useState<SolvedProblemType[]>(
    [],
  );
  const [problemList, setProblemList] = useState<SolvedProblem[]>([]);
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

  useEffect(() => {
    const fetchData = async () => {
      if (!id) return; // Ensure testId is available

      const testId = Array.isArray(id) ? id[0] : id;
      setLoading(true);
      try {
        const response = await getTestResultAPI(testId);

        setTotalCorrectCount(response.data.totalCorrectCount);
        setTotalSolvedTime(response.data.totalSolvedTime);
        setProblemTypeList(response.data.gradingResultByTypeList);
        setProblemList(response.data.gradingResultByProblemList);
      } catch (error) {
        console.error('Error fetching test results:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [id]);

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

  if (loading) {
    return (
      <div className={styles.container}>
        <Loading size="large" showText />
      </div>
    );
  }

  return (
    <div className={styles.container}>
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
