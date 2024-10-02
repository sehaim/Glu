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
  problemTypeCode: string;
  name: string;
  correctCount: number;
}

export interface Problem {
  problemId: number;
  title: string;
  content: string;
  metadata: ProblemOption;
  solution: string;
  problemLevel: ProblemLevel;
  problemType: ProblemType;
}

export interface SolvedProblem {
  problemId: number;
  title: string;
  content: string;
  metadata: ProblemOption;
  isCorrect: boolean;
  userAnswer: string;
  solution: string;
  solveTime: number;
}
