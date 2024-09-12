import InputItem from '@/components/common/inputs/inputItem';
import PrimaryButton from '@/components/common/buttons/primaryButton';
import { useState } from 'react';
import { LoginUser } from '@/types/UserTypes';
import { useDispatch } from 'react-redux';
import { login } from '@/store/authSlice';
import styles from '../userRegist.module.css';
import { login as loginAPI } from '../../utils/user/auth';

export default function Login() {
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');

  const dispatch = useDispatch();

  const handleLogin = async () => {
    const data: LoginUser = {
      id,
      password,
    };
    await loginAPI(data);
    dispatch(login());
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
