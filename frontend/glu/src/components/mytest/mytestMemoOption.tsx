import { useState } from 'react';
import styles from './mytestMemoOption.module.css';

interface MytestMemoOptionProps {
  onChange: (checked: boolean) => void;
}

export default function MytestMemoOption({ onChange }: MytestMemoOptionProps) {
  const [checked, setChecked] = useState(false);

  const handleCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const isChecked = e.target.checked;
    setChecked(isChecked);
    onChange(isChecked);
  };

  return (
    <div className={styles.container}>
      <input
        type="checkbox"
        checked={checked}
        onChange={handleCheckboxChange}
        className={styles.checkbox}
      />
      <div className={styles.label}>메모한 문제만 보기</div>
    </div>
  );
}
