import { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Image from 'next/image';
import { SolvedProblem, SolvedProblemType } from '@/types/ProblemTypes';
import dummyResults from '@/mock/dummyResults.json';
import RadarChart from '@/components/problem/result/radarChart';
import { formatTime } from '@/utils/problem/result';
import { RootState } from '@/store';
import LevelUpModal from '@/components/problem/result/levelUpModal';
import { resetLevel } from '@/store/levelupSlice';
import { FaCheck, FaTimes } from 'react-icons/fa';
import Loading from '@/components/common/loading';
import styles from './testResult.module.css';

interface ApiResponse {
  totalCorrectCount: number;
  totalSolvedTime: number; // 초 단위
  acquiredScore: number;
  totalScore: number;
  isStageUp: boolean;
  stageUpUrl: string;
  problemTypeList: SolvedProblemType[];
  problemList: SolvedProblem[];
}

export default function TestResult() {
  const dispatch = useDispatch();
  const [loading, setLoading] = useState<boolean>(true); // 로딩 상태 추가
  const [, setTotalCorrectCount] = useState<number | null>(null);
  const [totalSolvedTime, setTotalSolvedTime] = useState<number | null>(null);
  const [problemTypeList, setProblemTypeList] = useState<SolvedProblemType[]>(
    [],
  );
  const [problemList, setProblemList] = useState<SolvedProblem[]>([]);
  const [acquiredScore, setAcquiredScore] = useState<number | null>(null);
  const [totalScore, setTotalScore] = useState<number | null>(null);
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const { level, levelImage, isLeveledUp } = useSelector(
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
      setLoading(true); // 데이터를 가져오는 동안 로딩 상태를 true로 설정
      const response: ApiResponse = await new Promise((resolve) => {
        setTimeout(() => {
          return resolve(dummyResults);
        }, 0);
      });

      setTotalCorrectCount(response.totalCorrectCount);
      setTotalSolvedTime(response.totalSolvedTime);
      setProblemTypeList(response.problemTypeList);
      setProblemList(response.problemList);
      setAcquiredScore(response.acquiredScore);
      setTotalScore(response.totalScore);
      // setIsStageUp(response.isStageUp);
      // setStageUpUrl(response.stageUpUrl);
      setLoading(false); // 데이터가 다 로드되면 로딩 상태를 false로 설정
    };

    fetchData();
  }, []);

  const renderProblemRows = (startIndex: number, endIndex: number) => (
    <>
      <tr>
        {problemList.slice(startIndex, endIndex).map((problem, index) => (
          <td key={problem.problemId}>{startIndex + index + 1}</td>
        ))}
      </tr>
      <tr>
        {problemList.slice(startIndex, endIndex).map((problem) => (
          <td key={problem.problemId}>
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
          <h2 className={styles['levelUp-title']}>LV. {level}</h2>
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
              {acquiredScore}/{totalScore}
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
            <div key={problem.problemId} className={styles['solution-item']}>
              <p className={styles['solution-title']}>
                {index + 1}. {problem.title}
              </p>
              <div className={styles['solution-problem']}>
                <div className={styles['problem-content']}>
                  {problem.content}
                </div>
                <div className={styles['problem-option-list']}>
                  {problem?.problemOptions.map((problemOption, optionIndex) => (
                    <p
                      key={problemOption.problemOptionId}
                      className={`${styles['problem-option-item']} ${
                        Number(problem.userAnswer) === optionIndex + 1
                          ? styles['user-answer']
                          : ''
                      }`}
                    >
                      {optionIndex + 1}. {problemOption.option}{' '}
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
