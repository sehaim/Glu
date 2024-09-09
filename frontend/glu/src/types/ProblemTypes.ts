export interface ProblemOption {
  problemOptionId: number;
  option: string;
}

export interface ProblemLevel {
  problemLevelCode: string;
  name: string;
}

export interface ProblemType {
  problemTypeCode: string;
  problemTypeDetailCode: string;
  name: string;
}

export interface Problem {
  problemId: number;
  title: string;
  content: string;
  problemOptions: ProblemOption[];
  solution: string;
  problemLevel: ProblemLevel;
  problemType: ProblemType;
}
