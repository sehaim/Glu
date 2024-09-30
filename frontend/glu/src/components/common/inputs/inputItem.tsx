import { Birth } from '@/types/UserTypes';
import styles from './inputItem.module.css';
import BirthInputItem from './birthInputItem';

interface InputItemProps {
  value?: string;
  birth?: Birth;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  onBirthChange?: (birth: Birth) => void;
  label: string;
  placeholder?: string;
  direction?: string;
  canEdit?: boolean;
  isBirth?: boolean;
  children?: React.ReactNode;
}

export default function InputItem({
  value,
  birth,
  onChange,
  onBirthChange,
  label,
  placeholder = '',
  direction = 'column',
  canEdit = true,
  isBirth = false,
  children,
}: InputItemProps) {
  return (
    <div className={styles.container} id={styles[direction]}>
      <div className={styles['label-container']}>
        <div className={styles['input-label']}>{label}</div>
        {children}
      </div>
      <div id={styles[`${direction}-input`]}>
        {isBirth ? (
          <BirthInputItem value={birth} onChange={onBirthChange} />
        ) : (
          <input
            type={label.includes('비밀번호') ? 'password' : 'text'}
            value={value}
            onChange={onChange}
            placeholder={placeholder}
            disabled={!canEdit}
          />
        )}
      </div>
    </div>
  );
}
