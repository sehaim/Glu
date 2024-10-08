import styles from './problemContentText.module.css';

interface ProblemContentTextProps {
  problemContent: string;
}

export default function ProblemContentText({
  problemContent,
}: ProblemContentTextProps) {
  return <div className={styles['problem-text']}>{problemContent}</div>;
}
