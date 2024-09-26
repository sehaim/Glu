import { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Image from 'next/image';
import { SolvedProblem, SolvedProblemType } from '@/types/ProblemTypes';
import dummyResults from '@/mock/dummyResults.json';
import RadarChart from '@/components/problem/result/radarChart';
import { formatTime } from '@/utils/problem/result';
import { RootState } from '@/store';
import LevelUpModal from '@/components/problem/result/levelUpModal';
import styles from './testResult.module.css';
import { resetLevel } from '@/store/levelupSlice';

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
          <td key={problem.problemId}>{problem.isCorrect ? '✔️' : '❌'}</td>
        ))}
      </tr>
    </>
  );

  if (loading) {
    return <div>문제 로딩 중...</div>; // 로딩 중일 때 표시할 메시지
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
            <h5 className={styles['item-title']}>내 점수</h5>
            <div
              className={`${styles['item-content']} ${styles['item-content-text']}`}
            >
              {acquiredScore}/{totalScore}
            </div>
          </div>
          <div className={styles['result-item-row']}>
            <h5 className={styles['item-title']}>걸린 시간</h5>
            <p
              className={`${styles['item-content']} ${styles['item-content-text']}`}
            >
              {formatTime(totalSolvedTime)}
            </p>
          </div>
          <div className={styles['result-item']}>
            <h5 className={styles['item-title']}>맞힌 문제</h5>
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
            <h5 className={styles['item-title']}>영역별 점수</h5>
            <div className={styles.canvasWrapper}>
              {/* RadarChart 컴포넌트 */}
              <RadarChart problemTypeList={problemTypeList} />
            </div>
          </div>
        </div>
      </div>
      {/* 문제 해설 */}
      <div className={styles['solution-container']}>
        <h5 className={styles['item-title']}>문제 해설</h5>
        <div className={styles['solution-list']}>
          {problemList.map((problem, index) => (
            <div key={problem.problemId} className={styles['solution-item']}>
              <p className={styles['solution-title']}>
                {index + 1}. {problem.title}
              </p>
              <div className={styles['problem-content']}>{problem.content}</div>
              <div className={styles['problem-option-list']}>
                {problem?.problemOptions.map((problemOption, optionIndex) => (
                  <p
                    key={problemOption.problemOptionId}
                    className={`${styles['problem-option-item']} ${
                      problem.userAnswer === optionIndex + 1
                        ? styles['user-answer'] // userAnswer와 같으면 붉은색으로 표시
                        : ''
                    }`}
                  >
                    {optionIndex + 1}. {problemOption.option}{' '}
                  </p>
                ))}
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
