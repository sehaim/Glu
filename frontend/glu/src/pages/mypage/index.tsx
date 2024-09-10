import InputItem from '@/components/common/inputs/inputItem';
import SecondaryButton from '@/components/common/buttons/secondaryButton';
import { useState } from 'react';
import Modal from '@/components/common/modal';
import styles from './mypage.module.css';

export default function Mypage() {
  const [showModal, setShowModal] = useState(false);

  const handleNicknameChnage = () => {};

  const handlePasswordChnage = () => {};

  const handleBirthChnage = () => {};

  const handleShowModal = () => {
    setShowModal(!showModal);
  };

  return (
    <div id="page-container">
      <div id="page-title">나의 정보</div>
      <div className={styles.section}>
        <div className={styles['inline-items']}>
          <div className={styles['input-container']}>
            <InputItem label="아이디" direction="row" canEdit={false} />
          </div>
        </div>
        <div className={styles['inline-items']}>
          <div className={styles['input-container']}>
            <InputItem label="닉네임" direction="row" />
          </div>
          <div>
            <SecondaryButton
              label="변경하기"
              size="medium"
              onClick={handleNicknameChnage}
            />
          </div>
        </div>
        <div className={styles['inline-items']}>
          <div className={styles['input-container']}>
            <InputItem label="비밀번호" direction="row" canEdit={false} />
          </div>
          <div>
            <SecondaryButton
              label="변경하기"
              size="medium"
              onClick={handleShowModal}
            />
          </div>
        </div>
        <div className={styles['inline-items']}>
          <div className={styles['input-container']}>
            <InputItem label="생년월일" direction="row" isBirth={true} />
          </div>
          <div>
            <SecondaryButton
              label="변경하기"
              size="medium"
              onClick={handleBirthChnage}
            />
          </div>
        </div>
      </div>
      <Modal show={showModal} title="비밀번호 변경" onClose={handleShowModal}>
        <div className={styles['modal-content']}>
          <InputItem
            label="현재 비밀번호"
            placeholder="현재 비밀번호를 입력해주세요."
          />
          <InputItem
            label="새로운 비밀번호"
            placeholder="8자 이상의 영문, 숫자, 특수기호"
          />
          <InputItem
            label="비밀번호 확인"
            placeholder="비밀번호를 다시 입력해주세요."
          />
        </div>
      </Modal>
    </div>
  );
}
