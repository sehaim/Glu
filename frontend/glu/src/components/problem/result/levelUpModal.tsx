/* eslint-disable import/no-extraneous-dependencies */
import { IoClose } from 'react-icons/io5';
import Confetti from 'react-confetti';
import throttle from 'lodash/throttle';
import { useEffect, useState } from 'react';
import styles from './levelUpModal.module.css';

interface ModalProps {
  show: boolean;
  children: React.ReactNode;
  onClose: () => void;
}

export default function LevelUpModal({ show, children, onClose }: ModalProps) {
  const [windowSize, setWindowSize] = useState({ width: 0, height: 0 });

  useEffect(() => {
    const handleResize = throttle(() => {
      setWindowSize({ width: window.innerWidth, height: window.innerHeight });
    }, 100);

    handleResize();
    window.addEventListener('resize', handleResize);

    return () => {
      window.removeEventListener('resize', handleResize);
      handleResize.cancel();
    };
  }, []);

  const handleOverlayClick = (
    e: React.MouseEvent<HTMLDivElement, MouseEvent>,
  ) => {
    if (e.target === e.currentTarget) {
      onClose();
    }
  };

  if (!show) {
    return null;
  }

  return (
    <div className={styles['modal-overlay']} onClick={handleOverlayClick}>
      <Confetti width={windowSize.width} height={windowSize.height} />
      <div className={styles.container} onClick={(e) => e.stopPropagation()}>
        <IoClose className={styles['close-icon']} size={30} onClick={onClose} />
        <div className={styles.section}>
          <div className={styles.content}>{children}</div>
        </div>
      </div>
    </div>
  );
}
