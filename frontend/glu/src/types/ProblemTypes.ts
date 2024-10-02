export interface ProblemOption {
  options: string | string[];
}

export interface QuestionType {
  code: string;
  name: string;
}
export interface ProblemLevel {
  code: string;
  name: string;
}

export interface ProblemType {
  code: string;
  name: string;
}

export interface SolvedProblemType {
  correctCount: number;
  problemType: {
    problemTypeCode: string;
    name: string;
  };
}

export interface PreviousSolvedProblemType {
  correctCount: number;
  problemType: ProblemType;
  acquiredScore: number;
  totalScore: number;
}

export interface Problem {
  problemId: string;
  title: string;
  content: string;
  questionType: QuestionType;
  problemLevel: ProblemLevel;
  problemType: ProblemType;
  metadata: ProblemOption;
  solution: string;
}

export interface SolvedProblem {
  problemId: string;
  title: string;
  content: string;
  metadata: ProblemOption;
  isCorrect: boolean;
  userAnswer: string;
  solution: string;
  solveTime: number;
}
