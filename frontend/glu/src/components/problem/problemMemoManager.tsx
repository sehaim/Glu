import { useState } from 'react';
import SecondaryButton from '@/components/common/buttons/secondaryButton';
import ProblemMemoIcon from '@/components/problem/problemMemoIcon';
import { Memo } from '@/types/MemoTypes';
import styles from './problemMemoManager.module.css';

interface MemoManagerProps {
  memoList: Memo[];
  onSaveMemo: (memo: Memo) => void;
}

export default function ProblemMemoManager({
  memoList,
  onSaveMemo,
}: MemoManagerProps) {
  const [isEditingMemo, setIsEditingMemo] = useState<boolean>(false);
  const [newMemoContent, setNewMemoContent] = useState<string>('');
  const [selectedMemoIndex, setSelectedMemoIndex] = useState<number | null>(
    null,
  );

  const handleMemoClick = (index: number) => {
    const selectedMemo = memoList[index];
    setSelectedMemoIndex(index);
    setNewMemoContent(selectedMemo.content);
    setIsEditingMemo(true);
  };

  const handleMemoSave = () => {
    if (selectedMemoIndex !== null) {
      onSaveMemo({
        memoId: memoList[selectedMemoIndex].memoId,
        content: newMemoContent,
      });
    } else {
      onSaveMemo({ memoId: memoList.length + 1, content: newMemoContent });
    }
    setIsEditingMemo(false);
    setNewMemoContent('');
    setSelectedMemoIndex(null);
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

  return (
    <div className={styles['right-container']}>
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
        {memoList.map((memo, index) => (
          <ProblemMemoIcon
            key={memo.memoId}
            onClick={() => handleMemoClick(index)}
            role="button"
            tabIndex={0}
          />
        ))}
      </div>
    </div>
  );
}
