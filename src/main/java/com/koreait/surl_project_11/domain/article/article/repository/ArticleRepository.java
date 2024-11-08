package com.koreait.surl_project_11.domain.article.article.repository;

import com.koreait.surl_project_11.domain.article.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 제네릭이 <Article, Long>인 이유 : Article의 PK가 id인데 id의 타입이 long 이므로
public interface ArticleRepository extends JpaRepository<Article, Long> {

    // SQL1 (SELECT * FROM article WHERE id = IN (1, 2, 3) ORDER BY title DESC, id ASC)을 JpaRepository 메서드로 구현
    public List<Article> findByIdInOrderByTitleDescIdAsc(List<Long> ids);

    // SQL2 (SELECT * FROM article WHERE title LIKE = '%?%')을 JpaRepository 메서드로 구현
    public List<Article> findByTitleContaining(String keyword); // WHERE title LIKE %?%

    // SQL3 (SELECT * FROM article WHERE title = '?' AND `body` = '?')을 JpaRepository 메서 드로 구현
    public List<Article> findByTitleAndBody(String title, String body); // WHERE title = ? AND `body` = ?
}
