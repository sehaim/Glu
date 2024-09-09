import styles from './birthInputItem.module.css';

interface BirthInputItemProps {
  canEdit?: boolean;
}

export default function BirthInputItem({
  canEdit = true,
}: BirthInputItemProps) {
  const years: number[] = Array.from(
    { length: 11 },
    (_, index) => 2009 + index,
  );
  const months: number[] = Array.from({ length: 12 }, (_, index) => 1 + index);
  const days: number[] = Array.from({ length: 31 }, (_, index) => 1 + index);

  return (
    <div className={styles.container}>
      <div className={styles.section}>
        <select name="year" disabled={!canEdit}>
          {years.map((year) => (
            <option key={year} value={year}>
              {year}
            </option>
          ))}
        </select>
        <div className={styles['input-label']}>년</div>
      </div>
      <div className={styles.section}>
        <select name="month" disabled={!canEdit}>
          {months.map((month) => (
            <option key={month} value={month}>
              {month}
            </option>
          ))}
        </select>
        <div className={styles['input-label']}>월</div>
      </div>
      <div className={styles.section}>
        <select name="day" disabled={!canEdit}>
          {days.map((day) => (
            <option key={day} value={day}>
              {day}
            </option>
          ))}
        </select>
        <div className={styles['input-label']}>일</div>
      </div>
    </div>
  );
}
