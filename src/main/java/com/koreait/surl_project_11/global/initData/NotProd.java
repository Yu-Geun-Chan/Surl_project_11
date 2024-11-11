package com.koreait.surl_project_11.global.initData;

import com.koreait.surl_project_11.domain.article.article.entity.Article;
import com.koreait.surl_project_11.domain.article.article.service.ArticleService;
import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Profile("!prod") // !prod == dev(개발 모드) or test(테스트 모드) 일 때 실행하겠다.
@Configuration
@RequiredArgsConstructor
public class NotProd {

    // this를 통한 객체 내부에서의 메서드 호출은 @Transactional을 작동시키지 않는다.
    // 외부객체에 의한 메서드 호출은 @Transactional이 작동한다.
    // @Lazy, @Autowired 조합은 this의 외부 호출 모드 버전 self를 얻을 수 있다.
    // self를 통한 메서드 호출은 @Transactional을 작동 시킬 수 있다.
    @Lazy
    @Autowired
    private NotProd self;

    private final ArticleService articleService;
    private final MemberService memberService;

    @Bean // 개발자가 new 하지 않아도 스프링부트가 직접 관리하는 객체 -> 실행될 때 자동으로!
    public ApplicationRunner initNotProd() {
        return args -> {
            self.work1();
            self.work2();
        };
    }

    @Transactional
    public void work1() {
        // 읽기전용 트랜잭션
        // Article 테이블에 데이터가 이미 존재한다면 종료
        // (TRUNCATE 개념 -> 테이블 초기화, AUTO_INCREMENT 적용됐던 것도 삭제.)
        if (articleService.count() > 0) return;

        // 쓰기전용 트랜잭션
        Member member1 = memberService.join("user1", "1234", "유저 1").getData();
        Member member2 = memberService.join("user2", "1234", "유저 2").getData();

        Article article1 = articleService.write(member1, "제목 1", "내용 1").getData();
        Article article2 = articleService.write(member1, "제목 2", "내용 2").getData();

        Article article3 = articleService.write(member2, "제목 1", "내용 1").getData();
        Article article4 = articleService.write(member2, "제목 2", "내용 2").getData();


        article2.setTitle("제목2-2");

        articleService.delete(article1);
    }

    @Transactional
    public void work2() {
        // List : 0 ~ N (넣을 수 있는 값의 개수)
        // Optional : 0 ~ 1 (넣을 수 있는 값의 개수)
        Optional<Article> opArticle = articleService.findById(2L); // JpaRepository 기본 제공

        List<Article> articles = articleService.findAll(); // JpaRepository 기본 제공
    }

}

