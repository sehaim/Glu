import MytestTestCardList from '@/components/mytest/mytestTestCardList';
import { getSolvedTypeTestAPI } from '@/utils/user/mytest';
import styles from './mytest.module.css';

export const getServerSideProps: GetServerSideProps = async (context) => {
  const testData_01 = await getSolvedTypeTestAPI(
    'PT01',
    0,
    undefined,
    undefined,
    true,
    context,
  );

  const testList_01 = testData_01?.content;
  const totalPages_01 = testData_01?.totalPages;

  const testData_02 = await getSolvedTypeTestAPI(
    'PT02',
    0,
    undefined,
    undefined,
    true,
    context,
  );

  const testList_02 = testData_02?.content;
  const totalPages_02 = testData_02?.totalPages;

  const testData_03 = await getSolvedTypeTestAPI(
    'PT03',
    0,
    undefined,
    undefined,
    true,
    context,
  );

  const testList_03 = testData_03?.content;
  const totalPages_03 = testData_03?.totalPages;

  return {
    props: {
      testList_01,
      totalPages_01,
      testList_02,
      totalPages_02,
      testList_03,
      totalPages_03,
    },
  };
};

export default function MytestStarredPage() {
  return (
    <div className={styles.container}>
      <div>
        <MytestTestCardList />
      </div>
    </div>
  );
}
