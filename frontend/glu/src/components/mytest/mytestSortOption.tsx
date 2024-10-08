import { ChangeEvent } from 'react';

interface MytestSortOptionsProps {
  onChange: (value: string) => void;
}

export default function MytestSortOptions({
  onChange,
}: MytestSortOptionsProps) {
  const handleSelectChange = (e: ChangeEvent<HTMLSelectElement>) => {
    onChange(e.target.value);
  };

  return (
    <div>
      <select onChange={handleSelectChange}>
        <option value="createdDate,desc">최신순</option>
        <option value="createdDate,asc">오래된순</option>
        <option value="problem.problemLevelCode,desc">레벨 높은순</option>
        <option value="problem.problemLevelCode,asc">레벨 낮은순</option>
      </select>
    </div>
  );
}
