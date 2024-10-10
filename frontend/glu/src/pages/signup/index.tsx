import InputItem from '@/components/common/inputs/inputItem';
import PrimaryButton from '@/components/common/buttons/primaryButton';
import SecondaryButton from '@/components/common/buttons/secondaryButton';
import { useState, useEffect, useCallback } from 'react';
import { checkIdAPI, signupAPI } from '@/utils/user/signup';
import { SignupUser, Birth } from '@/types/UserTypes';
import {
  sweetalertConfirm,
  sweetalertError,
  hasEnglish,
  hasKorean,
  hasNumber,
  hasSpecialChar,
} from '@/utils/common';
import styles from '../userRegist.module.css';

export default function SignupPage() {
  const [id, setId] = useState('');
  const [isIdChecked, setIsIdChecked] = useState(false);
  const [nickname, setNickname] = useState('');
  const [password, setPassword] = useState('');
  const [passwordCheck, setPasswordCheck] = useState('');
  const [birth, setBirth] = useState<Birth>({ year: 2009, month: 1, day: 1 });
  const [passwordCheckError, setPasswordCheckError] = useState('');
  const [idError, setIdError] = useState(false);
  const [nicknameError, setNicknameError] = useState(false);
  const [passwordError, setPasswordError] = useState(false);

  useEffect(() => {
    if (password !== passwordCheck) {
      setPasswordCheckError('비밀번호가 일치하지 않습니다');
    } else {
      setPasswordCheckError('');
    }
  }, [password, passwordCheck]);

  const handleCheckId = async () => {
    if (
      id.length < 6 ||
      !hasEnglish(id) ||
      !hasNumber(id) ||
      hasSpecialChar(id) ||
      hasKorean(id)
    ) {
      setIdError(true);
    } else {
      const isDuplicate = await checkIdAPI(id);

      if (!isDuplicate) {
        sweetalertConfirm(
          '아이디 중복 확인',
          '사용 가능한 아이디입니다.',
          '사용하기',
        );
        setIsIdChecked(true);
      } else {
        sweetalertError('아이디 중복 확인', '사용할 수 없는 아이디입니다.');
        setId('');
      }
    }
  };

  useEffect(() => {
    setIsIdChecked(false);
    setIdError(false);
  }, [id]);

  useEffect(() => {
    setNicknameError(false);
  }, [nickname]);

  useEffect(() => {
    setPasswordError(false);
  }, [password]);

  const handleBirthChange = useCallback((newBirth: Birth) => {
    setBirth(newBirth);
  }, []);

  const handleSignUp = async () => {
    const isIdError =
      id.length < 6 ||
      !hasEnglish(id) ||
      !hasNumber(id) ||
      hasSpecialChar(id) ||
      hasKorean(id);

    const isNicknameError =
      nickname.length < 2 || hasSpecialChar(nickname) || hasNumber(nickname);

    const isPasswordError =
      password.length < 8 ||
      !hasEnglish(password) ||
      !hasNumber(password) ||
      !hasSpecialChar(password) ||
      password !== passwordCheck ||
      hasKorean(password);

    // 상태 업데이트
    await Promise.all([
      setIdError(isIdError),
      setNicknameError(isNicknameError),
      setPasswordError(isPasswordError),
    ]);

    if (isIdError || isNicknameError || isPasswordError) {
      return;
    }

    if (!isIdChecked) {
      const result = await sweetalertError(
        '아이디 중복',
        '아이디 중복을 확인해주세요.',
      );
      if (result.isConfirmed) {
        return;
      }
    }

    if (!isIdError && !isNicknameError && !isPasswordError && isIdChecked) {
      const data: SignupUser = {
        id,
        password,
        nickname,
        birth: `${birth.year}-${String(birth.month).padStart(2, '0')}-${String(birth.day).padStart(2, '0')}`,
      };
      await signupAPI(data);
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.section}>
        <div className={styles.title}>회원가입</div>
        <div className={styles['input-container']}>
          <InputItem
            value={id}
            onChange={(e) => setId(e.target.value)}
            label="아이디"
            placeholder="6자 이상의 영문, 숫자"
            error={idError}
            errorMsg={
              idError
                ? '아이디는 6자 이상의 영문, 숫자를 포함해야 합니다.'
                : undefined
            }
          >
            <SecondaryButton
              label="중복 확인"
              size="small"
              onClick={handleCheckId}
            />
          </InputItem>
          <InputItem
            value={nickname}
            onChange={(e) => setNickname(e.target.value)}
            label="닉네임"
            placeholder="2자 이상의 영문, 한글"
            error={nicknameError}
            errorMsg={
              nicknameError
                ? '닉네임은 2자 이상의 영문 또는 한글만 포함해야 합니다.'
                : undefined
            }
          />
          <InputItem
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            label="비밀번호"
            placeholder="8자 이상의 영문, 숫자, 특수문자"
            error={passwordError}
            errorMsg={
              passwordError && password === passwordCheck
                ? '비밀번호는 8자 이상의 영문, 숫자, 특수문자를 포함해야 합니다.'
                : undefined
            }
          />
          <InputItem
            value={passwordCheck}
            onChange={(e) => setPasswordCheck(e.target.value)}
            label="비밀번호 확인"
            placeholder="비밀번호 확인"
            error={passwordError}
          >
            <div className={styles['password-error']}>{passwordCheckError}</div>
          </InputItem>
          <InputItem
            label="생년월일"
            isBirth
            onBirthChange={handleBirthChange}
          />
        </div>
        <PrimaryButton label="가입하기" size="medium" onClick={handleSignUp} />
      </div>
    </div>
  );
}
