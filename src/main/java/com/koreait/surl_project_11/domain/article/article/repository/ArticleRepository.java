package com.koreait.surl_project_11.domain.article.article.repository;

import com.koreait.surl_project_11.domain.article.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

// 제네릭이 <Article, Long>인 이유 : Article의 PK가 id인데 id의 타입이 long 이므로
public interface ArticleRepository extends JpaRepository<Article, Long> {

}
