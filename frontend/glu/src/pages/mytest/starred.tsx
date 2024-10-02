import styles from './mytest.module.css';
import TestCardList from '@/components/test/testCardList';

export const getServerSideProps: GetServerSideProps = async (context) => {
  return {
    props: {},
  };
};

export default function MytestStarredPage() {
  return (
    <div className={styles.container}>
      <div>
        <TestCardList />
      </div>
    </div>
  );
}
