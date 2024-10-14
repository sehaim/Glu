import InputItem from '@/components/common/inputs/inputItem';
import SecondaryButton from '@/components/common/buttons/secondaryButton';
import { useCallback, useEffect, useState } from 'react';
import Modal from '@/components/common/modal';
import { GetServerSideProps } from 'next';
import { getUserInfoAPI, parseDate, putUserInfoAPI } from '@/utils/user/mypage';
import { Birth, MypageUser } from '@/types/UserTypes';
import { useDispatch } from 'react-redux';
import { login } from '@/store/authSlice';
import { getCookie } from 'cookies-next';
import {
  hasEnglish,
  hasKorean,
  hasNumber,
  hasSpecialChar,
  sweetalertError,
} from '@/utils/common';
import Head from 'next/head';
import styles from './mypage.module.css';

export const getServerSideProps: GetServerSideProps = async (context) => {
  const { req, res } = context;
  const accessToken = getCookie('accessToken', { req, res });

  if (!accessToken) {
    return {
      redirect: {
        destination: '/',
        permanent: false,
      },
    };
  }

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
  const [newPasswordCheck, setNewPasswordCheck] = useState('');
  const [birth, setBirth] = useState(currentBirth);
  const [passwordError, setPasswordError] = useState('');

  const dispatch = useDispatch();

  // 닉네임 변경
  const handleNicknameSubmit = () => {
    if (
      nickname.length < 2 ||
      hasNumber(nickname) ||
      hasSpecialChar(nickname)
    ) {
      sweetalertError(
        '닉네임 변경',
        '닉네임은 2자 이상의 영문 또는 한글만 포함해야 합니다.',
      );
      return;
    }
    putUserInfoAPI(nickname, undefined, undefined, undefined);
    dispatch(login({ nickname }));
  };

  // 비밀번호 변경
  const handleShowModal = () => {
    setShowModal(!showModal);
  };

  const handleCloseModal = () => {
    setShowModal(!showModal);
    setCurrentPassword('');
    setNewPassword('');
    setNewPasswordCheck('');
    setPasswordError('');
  };

  useEffect(() => {
    if (newPassword !== newPasswordCheck) {
      setPasswordError('비밀번호가 일치하지 않습니다');
    } else {
      setPasswordError('');
    }
  }, [newPassword, newPasswordCheck]);

  const handlePasswordSubmit = async () => {
    if (
      currentPassword === '' ||
      newPassword === '' ||
      newPasswordCheck === ''
    ) {
      sweetalertError('비밀번호 변경', '모든 항목을 정확히 입력해주세요.');
      return;
    }
    if (
      newPassword.length < 8 ||
      !hasEnglish(newPassword) ||
      !hasNumber(newPassword) ||
      !hasSpecialChar(newPassword) ||
      newPassword !== newPasswordCheck ||
      hasKorean(newPassword)
    ) {
      sweetalertError(
        '비밀번호 변경',
        '비밀번호는 8자 이상의<br>영문, 숫자, 특수문자를 포함해야 합니다.',
      );
      return;
    }
    await putUserInfoAPI(undefined, currentPassword, newPassword, undefined);
    handleCloseModal();
  };

  // 생년월일 변경
  const handleBirthChange = useCallback((newBirth: Birth) => {
    setBirth(newBirth);
  }, []);

  const handleBirthSubmit = async () => {
    const formattedBirth = `${birth.year}-${String(birth.month).padStart(2, '0')}-${String(birth.day).padStart(2, '0')}`;
    await putUserInfoAPI(undefined, undefined, undefined, formattedBirth);
  };

  const handleKeyDown = (event: React.KeyboardEvent) => {
    if (event.key === 'Enter') {
      event.preventDefault();
      handlePasswordSubmit();
    }
  };

  return (
    <div id="page-container">
      <Head>
        <title>나의 정보</title>
        <link rel="icon" type="image/png" sizes="32x32" href="/favicon.png" />
        <link rel="icon" type="image/x-icon" href="/favicon.ico" />
      </Head>
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
        onSubmit={handlePasswordSubmit}
        show={showModal}
        title="비밀번호 변경"
        onClose={handleCloseModal}
      >
        <div className={styles['modal-content']}>
          <InputItem
            value={currentPassword}
            label="현재 비밀번호"
            placeholder="현재 비밀번호를 입력해주세요."
            onChange={(e) => setCurrentPassword(e.target.value)}
            onKeyDown={handleKeyDown}
          />
          <InputItem
            value={newPassword}
            label="새로운 비밀번호"
            placeholder="8자 이상의 영문, 숫자, 특수기호"
            onChange={(e) => setNewPassword(e.target.value)}
            onKeyDown={handleKeyDown}
          />
          <InputItem
            value={newPasswordCheck}
            label="비밀번호 확인"
            placeholder="비밀번호를 다시 입력해주세요."
            onChange={(e) => setNewPasswordCheck(e.target.value)}
            onKeyDown={handleKeyDown}
          >
            <div className={styles['password-error']}>{passwordError}</div>
          </InputItem>
        </div>
      </Modal>
    </div>
  );
}
