import Link from 'next/link';

export default function Test() {
  return (
    <div>
      테스트보러가기
      <Link href="/test/problems">Go</Link>
    </div>
  );
}
