/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable consistent-return */
import React, { useEffect, useRef, useState } from 'react';
// eslint-disable-next-line import/no-extraneous-dependencies
import * as d3 from 'd3';
// eslint-disable-next-line import/no-extraneous-dependencies
import throttle from 'lodash/throttle';

interface RadarGraphProps {
  data: {
    axis: string;
    value: number;
  }[];
  maxScore: number;
}

export default function RadarGraph({ data, maxScore }: RadarGraphProps) {
  const chartRef = useRef<SVGSVGElement>(null);
  const containerRef = useRef<HTMLDivElement>(null);
  const [width, setWidth] = useState(0); // 동적으로 width 관리
  const [initialRender, setInitialRender] = useState(true); // 첫 번째 렌더링 여부

  useEffect(() => {
    const updateWidth = throttle(() => {
      if (containerRef.current) {
        setWidth(containerRef.current.offsetWidth);
      }
    }, 100); // 100ms 간격으로 throttling 적용

    // 페이지가 처음 로드될 때와 창 크기가 변경될 때 width를 업데이트
    window.addEventListener('resize', updateWidth);
    updateWidth();

    return () => {
      window.removeEventListener('resize', updateWidth);
      updateWidth.cancel(); // 이벤트 리스너 제거 시 throttle 해제
    };
  }, []);

  useEffect(() => {
    if (width === 0 || !chartRef.current) return; // width 값이 0일 때는 그리기를 중지

    const height = width;
    const radius = (Math.min(width, height) / 2) * 0.6;
    const levels = 4; // 원의 갯수
    const totalAxes = data.length;
    const angleSlice = (Math.PI * 2) / totalAxes;

    // 기존 SVG 내용을 제거하고 다시 그림
    d3.select(chartRef.current).selectAll('*').remove();

    const svg = d3
      .select(chartRef.current)
      .attr('width', width)
      .style('aspect-ratio', 1 / 1)
      .append('g')
      .style('transform', `translate(${width / 2}px, ${height / 2}px)`);

    // 원 그리기
    for (let level = 0; level < levels; level += 1) {
      const r = (radius / levels) * (level + 1);
      svg
        .append('circle')
        .attr('cx', 0)
        .attr('cy', 0)
        .attr('r', r)
        .style('fill', 'none')
        .style('stroke', 'var(--BORDER)')
        .style('stroke-opacity', 0.5);
    }

    // 축 그리기
    data.forEach((d, i) => {
      const angle = angleSlice * i - Math.PI / 2;
      const lineCoord = {
        x: radius * Math.cos(angle),
        y: radius * Math.sin(angle),
      };

      svg
        .append('line')
        .attr('x1', 0)
        .attr('y1', 0)
        .attr('x2', lineCoord.x)
        .attr('y2', lineCoord.y)
        .style('stroke', 'var(--BORDER_LIGHT)')
        .style('stroke-width', 1);

      // 축 라벨
      const labelCoord = {
        x: (radius + 35) * Math.cos(angle),
        y: (radius + 35) * Math.sin(angle),
      };
      svg
        .append('text')
        .attr('x', labelCoord.x)
        .attr('y', labelCoord.y)
        .attr('text-anchor', 'middle')
        .attr('font-family', 'PT')
        .attr('font-size', '16px')
        .attr('fill', 'var(--TEXT_SECONDARY)')
        .text(d.axis);
    });

    // 점수에 맞는 좌표 계산
    const radarLine = d3
      .lineRadial()
      .radius((d: [number, number]) => d[1])
      .angle((d: [number, number]) => d[0]);

    const radarData: [number, number][] = data.map((d, i) => [
      angleSlice * i,
      (d.value / maxScore) * radius,
    ]);

    radarData.push(radarData[0]);

    // 그래프 모양
    const linePath = svg
      .append('path')
      .datum(radarData)
      .attr('fill', 'rgba(255, 200, 200, 0.5)')
      .attr('stroke', 'var(--PINK)')
      .attr('stroke-width', 2)
      .attr('d', radarLine);

    // 점 추가하기
    const circles = svg
      .selectAll('.radarCircle')
      .data(radarData)
      .enter()
      .append('circle')
      .attr('class', 'radarCircle')
      .attr('r', 4)
      .style('fill', 'var(--PINK)')
      .attr('cx', (d) => Math.cos(d[0] - Math.PI / 2) * d[1])
      .attr('cy', (d) => Math.sin(d[0] - Math.PI / 2) * d[1]);

    // 애니메이션 적용
    if (initialRender) {
      linePath
        .style('opacity', 0)
        .transition()
        .duration(1500)
        .style('opacity', 1)
        .attrTween('d', function interpol() {
          const interpolate = d3.interpolateArray(
            radarData.map(([angle]) => [angle, 0]), // 반지름 0에서 시작
            radarData,
          );
          return function radarInterpol(t) {
            const line = radarLine(interpolate(t));
            return line === null ? '' : line;
          };
        })
        .on('end', () => setInitialRender(false));

      circles
        .attr('cx', (d) => Math.cos(d[0] - Math.PI / 2) * 0)
        .attr('cy', (d) => Math.sin(d[0] - Math.PI / 2) * 0)
        .transition()
        .duration(1500)
        .attr('cx', (d) => Math.cos(d[0] - Math.PI / 2) * d[1])
        .attr('cy', (d) => Math.sin(d[0] - Math.PI / 2) * d[1]);
    }

    // 마우스 호버 이벤트 추가
    circles
      .on('mouseover', function selectDot(event, d) {
        d3.select(this)
          .attr('r', 8)
          .style('fill', 'var(--DARKPINK')
          .style('cursor', 'pointer');
        svg
          .append('text')
          .attr('id', 'hoverText')
          .attr('fill', 'var(--DARKPINK)')
          .attr('font-family', 'PTBOLD')
          .attr('x', Math.cos(d[0] - Math.PI / 2) * d[1])
          .attr('y', Math.sin(d[0] - Math.PI / 2) * d[1])
          .text(`${Math.round((d[1] / radius) * maxScore)}`)
          .attr('text-anchor', 'middle')
          .attr('dy', -15);
      })
      .on('mouseout', function unselectDot() {
        d3.select(this).attr('r', 4).style('fill', 'var(--PINK)');
        d3.select('#hoverText').remove();
      });

    return () => {
      d3.select(chartRef.current).selectAll('*').remove();
    };
  }, [data, maxScore, width, initialRender]);

  return (
    <div ref={containerRef} style={{ width: '100%' }}>
      <svg ref={chartRef} />
    </div>
  );
}
