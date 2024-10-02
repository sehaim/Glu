import styles from './testCard.module.css';
import TestCardItem from './testCardItem';

export default function TestCardList() {
  return (
    <div className={styles.container}>
      <TestCardItem />
    </div>
  );
}
