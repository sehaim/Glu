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

export interface ProblemTypeDetail {
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

export interface Problem {
  problemId: string;
  title: string;
  content: string;
  questionType: QuestionType;
  problemLevel: ProblemLevel;
  problemType: ProblemType;
  problemTypeDetail: ProblemTypeDetail;
  metadata: ProblemOption;
  solution: string;
  isFavorite: boolean;
}

export interface SolvedProblem {
  problemId: string;
  title: string;
  content: string;
  questionType: QuestionType;
  problemLevel: ProblemLevel;
  problemType: ProblemType;
  problemTypeDetail: ProblemTypeDetail;
  metadata: ProblemOption;
  isCorrect: boolean;
  userAnswer: string;
  solution: string;
  isFavorite: boolean;
  solveTime: number;
  solveDate: string;
}

export interface SolvedProblemResponse {
  content: SolvedProblem[];
  page: number;
  size: number;
  totalPages: number;
  totalElements: number;
}
