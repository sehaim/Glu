import { useEffect, useState } from 'react';
import ProblemContentText from '@/components/problem/problemContentText';
import ProblemHeader from '@/components/problem/problemHeader';
import ProblemOptionList from '@/components/problem/problemOptionList';
import styles from './test.module.css';
import dummyProblems from '../../mock/dummyProblems.json';
import { Problem } from '../../types/ProblemTypes';

export default function Test() {
  const [problems, setProblems] = useState<Problem[]>([]);

  useEffect(() => {
    setProblems(dummyProblems);
  }, []);

  useEffect(() => {
    console.log(problems);
  }, [problems]);

  return (
    <div className={styles.container}>
      테스트
      <div className={styles['problem-wrapper']}>
        {problems.map((problem) => (
          <div className={styles.problem} key={problem.problemId}>
            <ProblemHeader
              problemLevel={problem?.problemLevel?.name}
              problemType={problem?.problemType?.name}
              problemTitle={problem?.title}
            />
            <div className={styles['problem-content']}>
              <ProblemContentText problemContent={problem?.content} />
              <ProblemOptionList problemOptions={problem?.problemOptions} />
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
