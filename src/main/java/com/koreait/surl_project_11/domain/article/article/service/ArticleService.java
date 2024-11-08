package com.koreait.surl_project_11.domain.article.article.service;

import com.koreait.surl_project_11.domain.article.article.entity.Article;
import com.koreait.surl_project_11.domain.article.article.repository.ArticleRepository;
import com.koreait.surl_project_11.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
// @Transactional : public 메서드에는 붙는게 관례
// readOnly = true : SELECT만 할거 같을 경우
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;

    public long count() {
        return articleRepository.count();
    }

    // 리턴
    // - 이번에 생성된 게시글 번호
    // - 게시글 생성에 대한 결과 메세지
    // - 결과 코드
    // - 10분 안에 5개 이상 작성 시 실패하도록
    @Transactional // (readOnly = true) 아닌경우는 따로 @Transactional 명시해줘야한다.
    public RsData<Article> write(String title, String body) {
        Article article = Article.builder()
                .title(title)
                .body(body)
                .build();

        articleRepository.save(article);

        return RsData.of("%d번 게시글이 생성되었습니다.".formatted(article.getId()), article);
    }

    public void delete(Article article) {
        articleRepository.delete(article);
    }

    public Optional<Article> findById(long id) {
        return articleRepository.findById(id);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }
}
