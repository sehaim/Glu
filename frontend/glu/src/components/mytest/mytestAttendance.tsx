/* eslint-disable react/no-array-index-key */
import { useEffect, useState } from 'react';
import { BsCaretLeftFill, BsCaretRightFill } from 'react-icons/bs';
import { Attendances } from '@/types/UserTypes';
import {
  format,
  endOfMonth,
  endOfWeek,
  isSameMonth,
  startOfMonth,
  startOfWeek,
  addDays,
  isSameDay,
  parseISO,
} from 'date-fns';
import Image from 'next/image';
import styles from './mytestAttendance.module.css';
import BarGraph from '../common/graphs/barGraph';

interface MytestAttendanceProps {
  attendanceRate: number;
  attendances: Attendances | null;
}

export default function MytestAttendance({
  attendanceRate,
  attendances,
}: MytestAttendanceProps) {
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

  useEffect(() => {
    console.log(attendances);
  }, [attendances]);

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

  // attendances 배열에 날짜가 존재하는지 확인하는 함수
  const hasAttendance = (day: Date) => {
    if (attendances) {
      return attendances.some((attendance) =>
        isSameDay(parseISO(attendance.date), day),
      );
    }
    return false;
  };

  const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
  return (
    <div className={styles.container}>
      <div className={styles.section}>
        <div className={styles['container-header']}>
          <div className={styles['section-name']}>출석율</div>
          <BarGraph maxScore={100} currentScore={attendanceRate} isPercentage />
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
              onClick={
                !isSameMonth(currentDate, today) ? handleNextMonth : undefined
              }
              className={`${styles['calendar-button']} ${isSameMonth(currentDate, today) ? styles['button-enabled'] : ''}`}
            />
          </div>
          <div className={styles['calendar-grid']}>
            {daysOfWeek.map((day) => (
              <div key={day} className={styles['calendar-day-header']}>
                {day}
              </div>
            ))}
            {generateCalendar(currentDate).map((day, index) => {
              return (
                <div
                  key={index}
                  className={`${styles['day-container']} ${isSameDay(day, today) && styles.today}`}
                >
                  <div
                    className={`${styles['calendar-day']} ${
                      !isSameMonth(day, currentDate) &&
                      styles['other-month-day']
                    } `}
                  >
                    {format(day, 'd')}
                  </div>
                  {hasAttendance(day) && (
                    <Image
                      src="/images/glu_character_shadow.png"
                      alt="attendance mark"
                      className={styles['attendance-icon']}
                      width={70}
                      height={60}
                    />
                  )}
                </div>
              );
            })}
          </div>
        </div>
      </div>
      <div className={styles.section}>
        <div className={styles['section-name']}>나의 테스트 기록</div>
        <div className={styles['test-list']}>테스트 기록</div>
      </div>
    </div>
  );
}
