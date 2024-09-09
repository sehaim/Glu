import styles from './inputItem.module.css';
import BirthInputItem from './birthInputItem';

interface InputItemProps {
  label: string;
  placeholder?: string;
  direction?: string;
  canEdit?: boolean;
  isBirth?: boolean;
  children?: React.ReactNode;
}

export default function InputItem({
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
      {isBirth ? (
        <BirthInputItem />
      ) : (
        <input type="text" placeholder={placeholder} disabled={!canEdit} />
      )}
    </div>
  );
}
