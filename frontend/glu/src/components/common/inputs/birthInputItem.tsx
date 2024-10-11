import { useState, useEffect } from 'react';
import { Birth } from '@/types/UserTypes';
import styles from './birthInputItem.module.css';

interface BirthInputItemProps {
  value?: Birth;
  onChange?: (birth: Birth) => void;
}

export default function BirthInputItem({
  value,
  onChange,
}: BirthInputItemProps) {
  const years: number[] = Array.from(
    { length: 11 },
    (_, index) => 2009 + index,
  );
  const months: number[] = Array.from({ length: 12 }, (_, index) => 1 + index);
  const days: number[] = Array.from({ length: 31 }, (_, index) => 1 + index);

  const [year, setYear] = useState(value ? value.year : years[0]);
  const [month, setMonth] = useState(value ? value.month : months[0]);
  const [day, setDay] = useState(value ? value.day : days[0]);

  useEffect(() => {
    if (onChange) {
      const newBirth: Birth = { year, month, day };
      onChange(newBirth);
    }
  }, [year, month, day, onChange]);

  return (
    <div className={styles.container}>
      <div className={styles.section}>
        <select
          name="year"
          value={year}
          onChange={(e) => setYear(Number(e.target.value))}
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
