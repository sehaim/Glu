import { ReactNode } from 'react';
import style from './global-layout.module.css';
import Header from '../common/header';

export default function GlobalLayout({ children }: { children: ReactNode }) {
  return (
    <div className={style.container}>
      <Header color="white" />
      <main className={style.main}>{children}</main>
      <footer className={style.footer}>푸터</footer>
    </div>
  );
}
