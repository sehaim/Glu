import InputItem from '@/components/common/inputs/inputItem';
import SecondaryButton from '@/components/common/buttons/secondaryButton';
import styles from './mypage.module.css';

export default function Mypage() {
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
            <SecondaryButton label="변경하기" size="medium" />
          </div>
        </div>
        <div className={styles['inline-items']}>
          <div className={styles['input-container']}>
            <InputItem label="비밀번호" direction="row" canEdit={false} />
          </div>
          <div>
            <SecondaryButton label="변경하기" size="medium" />
          </div>
        </div>
        <div className={styles['inline-items']}>
          <div className={styles['input-container']}>
            <InputItem label="생년월일" direction="row" isBirth={true} />
          </div>
          <div>
            <SecondaryButton label="변경하기" size="medium" />
          </div>
        </div>
      </div>
    </div>
  );
}
