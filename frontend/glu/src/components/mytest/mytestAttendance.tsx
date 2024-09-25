/* eslint-disable react/no-array-index-key */
import { useState } from 'react';
import { BsCaretLeftFill, BsCaretRightFill } from 'react-icons/bs';
import {
  format,
  endOfMonth,
  endOfWeek,
  isSameMonth,
  startOfMonth,
  startOfWeek,
  addDays,
  isAfter,
} from 'date-fns';
import styles from './mytestAttendance.module.css';
import BarGraph from '../common/graphs/barGraph';

export default function MytestAttendance() {
  const [currentDate, setCurrentDate] = useState(new Date());
  const today = new Date();

  // 해당 월의 날짜들 생성
  const generateCalendar = (date: Date) => {
    const monthStart = startOfMonth(date);
    const monthEnd = endOfMonth(monthStart);
    const startDate = startOfWeek(monthStart);
    const endDate = endOfWeek(monthEnd);

    const days: Date[] = [];
    let day = startDate;

    while (day <= endDate) {
      days.push(day);
      day = addDays(day, 1);
    }

    return days;
  };

  // 이전 달로 이동
  const handlePreviousMonth = () => {
    setCurrentDate(
      new Date(currentDate.getFullYear(), currentDate.getMonth() - 1),
    );
  };

  // 다음 달로 이동
  const handleNextMonth = () => {
    setCurrentDate(
      new Date(currentDate.getFullYear(), currentDate.getMonth() + 1),
    );
  };

  const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
  return (
    <div className={styles.container}>
      <div className={styles['container-header']}>
        <div className={styles['container-name']}>출석율</div>
        <BarGraph maxScore={100} currentScore={50} />
      </div>
      <div className={styles['calendar-container']}>
        <div className={styles['calendar-header']}>
          <BsCaretLeftFill
            size={35}
            onClick={handlePreviousMonth}
            className={styles['calendar-button']}
          />
          <span>
            {currentDate.getFullYear()}년 {currentDate.getMonth() + 1}월
          </span>
          <BsCaretRightFill
            size={35}
            onClick={handleNextMonth}
            className={styles['calendar-button']}
          />
        </div>
        <div className={styles['calendar-grid']}>
          {daysOfWeek.map((day) => (
            <div key={day} className={styles['calendar-day-header']}>
              {day}
            </div>
          ))}
          {generateCalendar(currentDate).map((day, index) => {
            if (!isSameMonth(day, today) && isAfter(day, today)) return null;
            return (
              <div
                key={index}
                className={`${styles['day-container']} ${isSameDay(day, today) && styles.today}`}
              >
                <div
                  className={`${styles['calendar-day']} ${
                    !isSameMonth(day, currentDate) && styles['other-month-day']
                  } `}
                >
                  {format(day, 'd')}
                </div>
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
}
