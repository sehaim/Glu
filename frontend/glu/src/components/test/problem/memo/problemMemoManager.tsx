import { useState } from 'react';
import SecondaryButton from '@/components/common/buttons/secondaryButton';
import { Memo } from '@/types/MemoTypes';
import {
  getProblemMemoAPI,
  postProblemMemoAPI,
  putProblemMemoAPI,
  deleteProblemMemoAPI,
} from '@/utils/problem/memo';
import { useQuery } from 'react-query';
import ProblemMemoIcon from '@/components/test/problem/memo/problemMemoIcon';
import styles from './problemMemoManager.module.css';

interface MemoManagerProps {
  problemId: string;
}

export default function ProblemMemoManager({ problemId }: MemoManagerProps) {
  const [isEditingMemo, setIsEditingMemo] = useState<boolean>(false);
  const [newMemoContent, setNewMemoContent] = useState<string>('');
  const [selectedMemoIndex, setSelectedMemoIndex] = useState<number | null>(
    null,
  );

  const { data: memos = [], refetch } = useQuery<Memo[]>(
    ['memos', problemId],
    () =>
      getProblemMemoAPI(problemId, 0, 100, 'createdDate,asc').then(
        (response) => response.content,
      ),
    {
      cacheTime: 30 * 60 * 1000, // 30분 동안 캐시 유지
      staleTime: 5 * 60 * 1000, // 5분 동안 신선한 데이터로 간주
    },
  );

  const handleMemoClick = (index: number) => {
    const selectedMemo = memos[index];
    setSelectedMemoIndex(index);
    setNewMemoContent(selectedMemo.content);
    setIsEditingMemo(true);
  };

  const handleMemoCancel = () => {
    setIsEditingMemo(false);
    setNewMemoContent('');
    setSelectedMemoIndex(null);
  };

  const handleNewMemo = () => {
    setIsEditingMemo(true);
    setSelectedMemoIndex(null);
    setNewMemoContent('');
  };

  const handleMemoSave = async () => {
    try {
      if (selectedMemoIndex !== null) {
        // 메모 수정
        await putProblemMemoAPI(
          problemId,
          memos[selectedMemoIndex].memoIndex,
          newMemoContent,
        );
      } else {
        // 새 메모 등록
        await postProblemMemoAPI(problemId, newMemoContent);
      }

      refetch();

      // 상태 초기화
      handleMemoCancel();
    } catch (error) {
      console.error('메모를 저장하는 중 문제가 발생했습니다.', error);
    }
  };

  const handleMemoDelete = async () => {
    if (selectedMemoIndex == null) return;

    try {
      await deleteProblemMemoAPI(problemId, memos[selectedMemoIndex].memoIndex);
      refetch();
      // 상태 초기화
      handleMemoCancel();
    } catch (error) {
      // 메모 삭제 중 에러발생
    }
  };

  // useEffect(() => {
  //   getMemos();
  // }, [problemId]);

  return (
    <div className={styles.container}>
      <h5 className={styles['container-title']}>나의 메모</h5>
      {isEditingMemo ? (
        <>
          <textarea
            className={styles['memo-textarea']}
            value={newMemoContent}
            onChange={(e) => setNewMemoContent(e.target.value)}
          />
          <div className={styles['memo-textarea-button-list']}>
            <SecondaryButton
              label="저장"
              size="small"
              onClick={handleMemoSave}
            />
            <SecondaryButton
              label="취소"
              size="small"
              onClick={handleMemoCancel}
            />
            {selectedMemoIndex !== null && (
              <SecondaryButton
                label="삭제"
                size="small"
                onClick={handleMemoDelete}
              />
            )}
          </div>
        </>
      ) : (
        <div className={styles['memo-textarea-button-list']}>
          <SecondaryButton
            label="메모 작성"
            size="small"
            onClick={handleNewMemo}
          />
        </div>
      )}
      <div className={styles['memo-list']}>
        {memos?.map((memo, index) => (
          <div key={memo.memoIndex} className={styles['memo-item']}>
            <ProblemMemoIcon
              onClick={() => handleMemoClick(index)}
              role="button"
              tabIndex={0}
            />
          </div>
        ))}
      </div>
    </div>
  );
}
