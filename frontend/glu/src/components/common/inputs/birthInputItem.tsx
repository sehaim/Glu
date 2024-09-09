import { useState, useEffect } from 'react';
import styles from './birthInputItem.module.css';

interface BirthInputItemProps {
  onChange?: (birth: string) => void;
  canEdit?: boolean;
}

export default function BirthInputItem({
  onChange,
  canEdit = true,
}: BirthInputItemProps) {
  const years: number[] = Array.from(
    { length: 11 },
    (_, index) => 2009 + index,
  );
  const months: number[] = Array.from({ length: 12 }, (_, index) => 1 + index);
  const days: number[] = Array.from({ length: 31 }, (_, index) => 1 + index);

  const [year, setYear] = useState(years[0]);
  const [month, setMonth] = useState(months[0]);
  const [day, setDay] = useState(days[0]);
  const [birth, setBirth] = useState('');

  useEffect(() => {
    setBirth(
      `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`,
    );
    if (onChange) {
      onChange(birth); // 부모 컴포넌트로 값 전달
    }
  }, [year, month, day, birth, onChange]);

  return (
    <div className={styles.container}>
      <div className={styles.section}>
        <select
          name="year"
          value={year}
          onChange={(e) => setYear(Number(e.target.value))}
          disabled={!canEdit}
        >
          {years.map((yearOption) => (
            <option key={yearOption} value={yearOption}>
              {yearOption}
            </option>
          ))}
        </select>
        <div className={styles['input-label']}>년</div>
      </div>
      <div className={styles.section}>
        <select
          name="month"
          value={month}
          onChange={(e) => setMonth(Number(e.target.value))}
          disabled={!canEdit}
        >
          {months.map((monthOption) => (
            <option key={monthOption} value={monthOption}>
              {monthOption}
            </option>
          ))}
        </select>
        <div className={styles['input-label']}>월</div>
      </div>
      <div className={styles.section}>
        <select
          name="day"
          value={day}
          onChange={(e) => setDay(Number(e.target.value))}
          disabled={!canEdit}
        >
          {days.map((dayOption) => (
            <option key={dayOption} value={dayOption}>
              {dayOption}
            </option>
          ))}
        </select>
        <div className={styles['input-label']}>일</div>
      </div>
    </div>
  );
}
