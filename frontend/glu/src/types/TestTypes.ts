import { ProblemType } from './ProblemTypes';

export interface ComprehensiveTestRecord {
  testId: string;
  totalCorrectCount: number;
  totalSolvedTime: number;
  createdDate: string;
  gradingResultByTypeList: [
    {
      correctCount: number;
      problemType: {
        code: string;
        name: string;
      };
      acquiredScore: number;
      totalScore: number;
    },
  ];
}

export interface PreviousSolvedTestType {
  correctCount: number;
  problemType: ProblemType;
  acquiredScore: number;
  totalScore: number;
}

// export interface GradingResultByProblemList []{
//   problemId: string,
//   title: string,
//   content: string,
//   // metadata: {
//   //   additionalProp1: {},
//   //   additionalProp2: {},
//   //   additionalProp3: {}
//   // },
//   solution: string,
//   isCorrect: boolean,
//   userAnswer: string,
//   solveTime: number,
//   questionType: {
//     code: string,
//     name: string
//   },
//   problemLevel: {
//     code: string,
//     name: string
//   },
//   problemTypeDetail: {
//     code: string,
//     name: string
//   },
//   problemType: {
//     code: string,
//     name: string
//   }
// }
