import InputItem from '@/components/common/inputs/inputItem';
import SecondaryButton from '@/components/common/buttons/secondaryButton';
import { useCallback, useState } from 'react';
import Modal from '@/components/common/modal';
import { GetServerSideProps } from 'next';
import { getUserInfoAPI, parseDate, putUserInfoAPI } from '@/utils/user/mypage';
import { Birth, MypageUser } from '@/types/UserTypes';
import styles from './mypage.module.css';

export const getServerSideProps: GetServerSideProps = async (context) => {
  // 서버에서 회원정보 API 호출
  const userInfo = await getUserInfoAPI(context);

  const currentBirth = parseDate(userInfo.birth);

  return {
    props: {
      userInfo,
      currentBirth,
    },
  };
};

interface MypageProps {
  userInfo: MypageUser;
  currentBirth: Birth;
}

export default function Mypage({ userInfo, currentBirth }: MypageProps) {
  const [showModal, setShowModal] = useState(false);
  const [nickname, setNickname] = useState(userInfo.nickname);
  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [birth, setBirth] = useState(currentBirth);

  const handleNicknameSubmit = () => {
    try {
      putUserInfoAPI(nickname, undefined, undefined, undefined);
      alert('닉네임이 변경되었습니다.');
    } catch {
      alert('닉네임 변경에 실패했습니다.'); // alert 추후 수정
    }
  };

  const handlePasswordChnage = () => {
    console.log('변경 완료'); // 추후 삭제
  };

  const handleBirthChange = useCallback((newBirth: Birth) => {
    setBirth(newBirth);
  }, []);

  const handleBirthSubmit = () => {
    const formattedBirth = `${birth.year}-${String(birth.month).padStart(2, '0')}-${String(birth.day).padStart(2, '0')}`;
    putUserInfoAPI(undefined, undefined, undefined, formattedBirth);
  };

  const handleShowModal = () => {
    setShowModal(!showModal);
  };

  return (
    <div id="page-container">
      <div id="page-title">나의 정보</div>
      <div className={styles.section}>
        <div className={styles['inline-items']}>
          <div className={styles['input-container']}>
            <InputItem
              value={userInfo.id}
              label="아이디"
              direction="row"
              canEdit={false}
            />
          </div>
        </div>
        <div className={styles['inline-items']}>
          <div className={styles['input-container']}>
            <InputItem
              value={nickname}
              label="닉네임"
              direction="row"
              onChange={(e) => setNickname(e.target.value)}
            />
          </div>
          <div>
            <SecondaryButton
              label="변경하기"
              size="medium"
              onClick={handleNicknameSubmit}
            />
          </div>
        </div>
        <div className={styles['inline-items']}>
          <div className={styles['input-container']}>
            <InputItem
              value="password"
              label="비밀번호"
              direction="row"
              canEdit={false}
            />
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
            <InputItem
              birth={currentBirth}
              label="생년월일"
              direction="row"
              isBirth
              onBirthChange={handleBirthChange}
            />
          </div>
          <div>
            <SecondaryButton
              label="변경하기"
              size="medium"
              onClick={handleBirthSubmit}
            />
          </div>
        </div>
      </div>
      <Modal
        onSubmit={handlePasswordChnage}
        show={showModal}
        title="비밀번호 변경"
        onClose={handleShowModal}
      >
        <div className={styles['modal-content']}>
          <InputItem
            value={currentPassword}
            label="현재 비밀번호"
            placeholder="현재 비밀번호를 입력해주세요."
            onChange={(e) => setCurrentPassword(e.target.value)}
          />
          <InputItem
            value={newPassword}
            label="새로운 비밀번호"
            placeholder="8자 이상의 영문, 숫자, 특수기호"
            onChange={(e) => setNewPassword(e.target.value)}
          />
          <InputItem
            value="password"
            label="비밀번호 확인"
            placeholder="비밀번호를 다시 입력해주세요."
          />
        </div>
      </Modal>
    </div>
  );
}
