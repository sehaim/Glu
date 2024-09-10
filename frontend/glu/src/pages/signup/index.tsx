import InputItem from '@/components/common/inputs/inputItem';
import PrimaryButton from '@/components/common/buttons/primaryButton';
import SecondaryButton from '@/components/common/buttons/secondaryButton';
import { useState, useEffect } from 'react';
import styles from '../userRegist.module.css';

export default function Login() {
  const [id, setId] = useState('');
  const [nickname, setNickname] = useState('');
  const [password, setPassword] = useState('');
  const [passwordCheck, setPasswordCheck] = useState('');
  const [birth, setBirth] = useState('');
  const [passwordError, setPasswordError] = useState('');

  useEffect(() => {
    if (password !== passwordCheck) {
      setPasswordError('비밀번호가 일치하지 않습니다');
    } else {
      setPasswordError('');
    }
  }, [password, passwordCheck]);

  const handleCheckId = () => {
    console.log('중복확인'); // 나중에 삭제
  };

  const handleSignUp = () => {
    console.log('회원가입'); // 나중에 삭제
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
          />
          <InputItem
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            label="비밀번호"
            placeholder="8자 이상의 영문, 숫자, 특수기호"
          />
          <InputItem
            value={passwordCheck}
            onChange={(e) => setPasswordCheck(e.target.value)}
            label="비밀번호 확인"
            placeholder="비밀번호 확인"
          >
            <div className={styles['password-error']}>{passwordError}</div>
          </InputItem>
          <InputItem
            value={birth}
            onBirthChange={(birthValue) => setBirth(birthValue)}
            label="생년월일"
            isBirth
          />
        </div>
        <PrimaryButton label="가입하기" size="medium" onClick={handleSignUp} />
      </div>
    </div>
  );
}
