/* eslint-disable jsx-a11y/no-static-element-interactions */
/* eslint-disable jsx-a11y/click-events-have-key-events */
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

  const handleOverlayClick = (
    e: React.MouseEvent<HTMLDivElement, MouseEvent>,
  ) => {
    if (e.target === e.currentTarget) {
      onClose();
    }
  };

  return (
    <div className={styles['modal-overlay']} onClick={handleOverlayClick}>
      <div className={styles.container} onClick={(e) => e.stopPropagation()}>
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
