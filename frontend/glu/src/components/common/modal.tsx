import { IoClose } from 'react-icons/io5';
import styles from './modal.module.css';
import PrimaryButton from './buttons/primaryButton';

interface ModalProps {
  show: boolean;
  title: string;
  children: React.ReactNode;
  onClose: () => void;
  onSubmit: () => void;
}

export default function Modal({
  show,
  title,
  children,
  onClose,
  onSubmit,
}: ModalProps) {
  if (!show) {
    return null;
  }

  return (
    <div className={styles['modal-overlay']}>
      <div className={styles.container}>
        <IoClose className={styles['close-icon']} size={30} onClick={onClose} />
        <div className={styles.section}>
          <div className={styles.title}>{title}</div>
          <div className={styles.content}>{children}</div>
          <div>
            <PrimaryButton label="변경하기" size="small" onClick={onSubmit} />
          </div>
        </div>
      </div>
    </div>
  );
}
