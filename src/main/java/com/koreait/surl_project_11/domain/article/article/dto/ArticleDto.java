package com.koreait.surl_project_11.domain.article.article.dto;

import com.koreait.surl_project_11.domain.article.article.entity.Article;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 보여주기용(Entity는 외부에 직접 노출시키면 안되기 때문)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ArticleDto {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private long authorId;
    private String authorName;
    private String title;
    private String body;

    public ArticleDto(Article article) {
        this.id = article.getId();
        this.createDate = article.getCreateDate();
        this.modifyDate = article.getModifyDate();
        this.authorId = article.getAuthor().getId();
        this.authorName = article.getAuthor().getName();
        this.title = article.getTitle();
        this.body = article.getBody();
    }
}
