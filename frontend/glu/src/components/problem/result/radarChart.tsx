import { useEffect, useRef } from 'react';
// eslint-disable-next-line import/no-extraneous-dependencies
import {
  Chart,
  RadarController,
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend,
} from 'chart.js';
import { SolvedProblemType } from '@/types/ProblemTypes';
import styles from './radarChart.module.css';

// Chart.js 모듈 등록
Chart.register(
  RadarController,
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend,
);

interface RadarChartProps {
  problemTypeList: SolvedProblemType[];
}

export default function RadarChart({ problemTypeList }: RadarChartProps) {
  const chartRef = useRef<HTMLCanvasElement>(null);
  const chartInstanceRef = useRef<Chart | null>(null); // Chart 인스턴스를 저장하는 ref

  useEffect(() => {
    const initializeChart = () => {
      if (chartRef.current && problemTypeList.length > 0) {
        const ctx = chartRef.current.getContext('2d');

        // 기존 차트가 있으면 삭제
        if (chartInstanceRef.current) {
          chartInstanceRef.current.destroy();
        }

        if (ctx) {
          const labels = problemTypeList.map((item) => {
            const { name } = item.problemType;
            const firstPart = name.slice(0, 4).trim();
            const restPart = name.slice(4).trim();
            return [firstPart, restPart];
          });

          const dataValues = problemTypeList.map((item) => item.correctCount);

          const data = {
            labels, // 공백 기준으로 줄바꿈된 labels
            datasets: [
              {
                label: '영역별 점수',
                data: dataValues,
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderColor: 'rgba(255, 99, 132, 1)',
                borderWidth: 1,
              },
            ],
          };

          chartInstanceRef.current = new Chart(ctx, {
            type: 'radar',
            data,
            options: {
              responsive: true,
              maintainAspectRatio: false,
              scales: {
                r: {
                  angleLines: {
                    display: true,
                  },
                  suggestedMin: 0,
                  suggestedMax: 5, // 차트의 최대값을 5로 설정
                  pointLabels: {
                    font: {
                      size: 14, // Adjust font size as needed
                    },
                    padding: 10,
                  },
                },
              },
              plugins: {
                legend: {
                  display: false,
                },
              },
            },
          });
        }
      }
    };

    if (problemTypeList.length > 0) {
      initializeChart();
    }
  }, [problemTypeList]); // problemTypeList가 변경될 때 차트 업데이트

  return <canvas ref={chartRef} className={styles.chartCanvas} />;
}
