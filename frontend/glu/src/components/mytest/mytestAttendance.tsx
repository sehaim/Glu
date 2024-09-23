import { useState } from 'react';
import styles from './mytestAttendance.module.css';
import { BsCaretLeftFill } from 'react-icons/bs';
import { BsCaretRightFill } from 'react-icons/bs';

export default function MytestAttendance() {
  const [currentDate, setCurrentDate] = useState(new Date());

  // 해당 월의 날짜들 생성
  const generateCalendar = (date: Date) => {
    const startOfMonth = new Date(date.getFullYear(), date.getMonth(), 1);
    const endOfMonth = new Date(date.getFullYear(), date.getMonth() + 1, 0);

    const daysInMonth = [];
    for (let i = startOfMonth.getDate(); i <= endOfMonth.getDate(); i++) {
      daysInMonth.push(new Date(date.getFullYear(), date.getMonth(), i));
    }
    return daysInMonth;
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
        {/* <div>출석율 그래프</div> */}
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
          {generateCalendar(currentDate).map((day, index) => (
            <div key={index} className={styles['day-container']}>
              <div className={styles['calendar-day']}>{day.getDate()}</div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
