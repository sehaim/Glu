import InputItem from '@/components/common/inputItem';
import styles from '../userRegist.module.css';

export default function Login() {
  return (
    <div className={styles.container}>
      <div className={styles.section}>
        <div className={styles.title}>로그인</div>
        <InputItem label="아이디" />
        <InputItem label="비밀번호" />
      </div>
    </div>
  );
}
