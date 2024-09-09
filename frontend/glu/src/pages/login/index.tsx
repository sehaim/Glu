import InputItem from '@/components/common/inputs/inputItem';
import PrimaryButton from '@/components/common/buttons/primaryButton';
import { useState } from 'react';
import styles from '../userRegist.module.css';

export default function Login() {
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = () => {
    console.log('로그인'); // 나중에 삭제
  };

  return (
    <div className={styles.container}>
      <div className={styles.section}>
        <div className={styles.title}>로그인</div>
        <div className={styles['input-container']}>
          <InputItem
            value={id}
            onChange={(e) => setId(e.target.value)}
            label="아이디"
          />
          <InputItem
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            label="비밀번호"
          />
        </div>
        <PrimaryButton label="로그인" size="medium" onClick={handleLogin} />
      </div>
    </div>
  );
}
