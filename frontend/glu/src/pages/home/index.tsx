import { useEffect, useRef } from 'react';
import styles from './home.module.css';

export default function Home() {
  const section1Ref = useRef(null);
  const section1TextRef = useRef(null);
  const section1ImageRef = useRef(null);
  const section2Ref = useRef(null);
  const section2TextRef = useRef(null);
  const section2ContentRef = useRef(null);

  useEffect(() => {
    const observerOptions = {
      threshold: 0.3, // 30% 이상 보일 때 트리거
    };

    const observer = new IntersectionObserver((entries) => {
      entries.forEach((entry) => {
        if (entry.target === section1Ref.current) {
          if (entry.isIntersecting) {
            section1TextRef.current.classList.add(styles.visible);
            section1TextRef.current.classList.remove(styles.hidden);
            section1ImageRef.current.classList.add(styles.visible);
            section1ImageRef.current.classList.remove(styles.hidden);
          } else {
            section1TextRef.current.classList.add(styles.hidden);
            section1TextRef.current.classList.remove(styles.visible);
            section1ImageRef.current.classList.add(styles.hidden);
            section1ImageRef.current.classList.remove(styles.visible);
          }
        }

        if (entry.target === section2Ref.current) {
          if (entry.isIntersecting) {
            section2TextRef.current.classList.add(styles.visible);
            section2TextRef.current.classList.remove(styles.hidden);
            section2ContentRef.current.classList.add(styles.visible);
            section2ContentRef.current.classList.remove(styles.hidden);
          } else {
            section2TextRef.current.classList.add(styles.hidden);
            section2TextRef.current.classList.remove(styles.visible);
            section2ContentRef.current.classList.add(styles.hidden);
            section2ContentRef.current.classList.remove(styles.visible);
          }
        }
      });
    }, observerOptions);

    if (section1Ref.current) observer.observe(section1Ref.current);
    if (section2Ref.current) observer.observe(section2Ref.current);

    return () => {
      if (section1Ref.current) observer.unobserve(section1Ref.current);
      if (section2Ref.current) observer.unobserve(section2Ref.current);
    };
  }, []);

  return (
    <div className={styles.container}>
      <section ref={section1Ref} className={`${styles.section}`}>
        <div
          id={styles.section1_text_wrapper}
          ref={section1TextRef}
          className={styles.hidden}
        >
          <h3 className={styles.section_title}>환영합니다!</h3>
          <p className={styles.section_describe}>
            문해력 향상을 위한 첫걸음, 레벨 테스트를 시작해보세요.
            <br />
            현재 수준을 정확히 파악하여 가장 적합한 학습 콘텐츠를 제공하기 위해
            준비되었습니다.
          </p>
        </div>
        <div
          id={styles.section1_image_wrapper}
          ref={section1ImageRef}
          className={styles.hidden}
        >
          <img
            id={styles.main_character}
            src="/images/glu_character.png"
            alt="Glu Character"
          />
        </div>
      </section>
      <section
        ref={section2Ref}
        className={`${styles.section} ${styles.section2}`}
      >
        <div
          id={styles.section2_text_wrapper}
          ref={section2TextRef}
          className={styles.hidden}
        >
          <h3 className={styles.section_title}>글루레벨테스트</h3>
          <p className={styles.section_describe}>
            레벨 테스트는 문해력 진단을 위한 3가지 문제 유형으로 구성되어
            있습니다.
          </p>
        </div>
        <div
          id={styles.section2_content_wrapper}
          ref={section2ContentRef}
          className={styles.hidden}
        >
          <h4 className={styles.section2_title}>단어와 문장규칙</h4>
          <p className={styles.section2_describe}>
            단어의 의미를 이해하고, 문법적으로 올바른 문장을 구성할 수 있는지를
            평가합니다.
            <br />
            어휘의 폭을 넓히고 문법의 기초를 다지는 문제들이 포함되어 있습니다.
          </p>
          <h4 className={styles.section2_title}>글 읽고 이해하기</h4>
          <p className={styles.section2_describe}>
            글을 읽고 그 내용을 정확하게 이해하는지, 중요한 정보를 파악하는
            능력을 중점적으로 평가합니다.
            <br />
            읽기 자료를 통해 의미를 추출하고, 내용을 요약하거나 질문에 답할 수
            있는 문제들이 포함되어 있습니다.
          </p>
          <h4 className={styles.section2_title}>추측하기</h4>
          <p className={styles.section2_describe}>
            단순히 글을 읽고 이해하는 것을 넘어, 읽은 내용을 바탕으로 논리적인
            결론을 도출할 수 있는지를 평가합니다.
            <br />
            글의 맥락을 이해하고, 주어진 정보를 바탕으로 추론하는 문제들이
            포함되어 있습니다.
          </p>
        </div>
      </section>
      <section className={`${styles.section} ${styles.section3}`}>
        <h3 className={styles.section_title}>성장에맞춘학습</h3>
        <div className={styles.section3_content_wrapper}>
          <img
            id={styles.graph_image}
            src="/images/home/home_graph.png"
            alt="Graph Image"
          />
          <p className={styles.section_describe}>
            세 가지 유형의 테스트 결과를 바탕으로 그래프를 제공하여 학습 상태를
            시각적으로 보여줍니다.
            <br />
            그래프를 통해 각 유형별 성취도를 쉽게 확인할 수 있으며, 결과에 따라
            유형별로 맞춤형 학습 난이도를 조정하고 적절한 학습 콘텐츠를 추천해
            드립니다.
            <br />
            따라서 학습 성장을 명확하게 추적하고, 지속적으로 향상시킬 수
            있습니다.
          </p>
        </div>
        <div className={styles.section3_content_wrapper}>
          <button className={styles.section3_button}>테스트 보러가기</button>
        </div>
      </section>
    </div>
  );
}
