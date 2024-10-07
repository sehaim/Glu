import { GetServerSideProps } from 'next';
import TestCardList from '@/components/test/testCardList';
import styles from './mytest.module.css';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
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
