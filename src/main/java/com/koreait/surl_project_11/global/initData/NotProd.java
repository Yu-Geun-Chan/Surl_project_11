package com.koreait.surl_project_11.global.initData;

import com.koreait.surl_project_11.domain.article.article.entity.Article;
import com.koreait.surl_project_11.domain.article.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

@Profile("!prod") // !prod == dev(개발 모드) or test(테스트 모드) 일 때 실행하겠다.
@Configuration
@RequiredArgsConstructor
public class NotProd {
    private final ArticleRepository articleRepository;

    @Bean // 개발자가 new 하지 않아도 스프링부트가 직접 관리하는 객체 -> 실행될 때 자동으로!
    public ApplicationRunner initNotProd() {
        return new ApplicationRunner() {
            @Override
            @Transactional
            public void run(ApplicationArguments args) throws Exception {

                // 읽기전용 트랜젝션
                // Article 테이블에 데이터가 이미 존재한다면 종료
                // (TRUNCATE 개념 -> 테이블 초기화, AUTO_INCREMENT 적용됐던 것도 삭제.)
                if (articleRepository.count() > 0) return;

                // Article 테이블의 데이터를 지우고 실행하겠다
                // (DELETE 개념 -> 단순 삭제, AUTO_INCREMENT는 적용된건 날리지 못하기에 id값은 증가된 상태.)
//                  articleRepository.deleteAll();

                Article article1 = Article.builder().
                        title("제목1")
                        .body("내용1").build();

                Article article2 = Article.builder().
                        title("제목2")
                        .body("내용2").build();

                // 쓰기전용 트랜젝션
                articleRepository.save(article1);
                articleRepository.save(article2);

                article2.setTitle("제목2-2");

                articleRepository.delete(article1);

            }

            ;
        };
    }
}
