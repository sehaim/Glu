import styles from './inputItem.module.css';
import BirthInputItem from './birthInputItem';

interface InputItemProps {
  label: string;
  placeholder?: string;
  canEdit?: boolean;
  isBirth?: boolean;
}

export default function InputItem({
  label,
  placeholder = '',
  canEdit = true,
  isBirth = false,
}: InputItemProps) {
  return (
    <div className={styles.container}>
      <div className={styles['input-label']}>{label}</div>
      {isBirth ? (
        <BirthInputItem />
      ) : (
        <input type="text" placeholder={placeholder} disabled={!canEdit} />
      )}
    </div>
  );
}
