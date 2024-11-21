package com.koreait.surl_project_11.domain.article.article.controller;

import com.koreait.surl_project_11.domain.article.article.entity.Article;
import com.koreait.surl_project_11.domain.article.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/article/list")
    @ResponseBody
    public String showList() {
        StringBuilder sb = new StringBuilder();

        List<Article> articles = articleService.findAll();

        sb.append("<h1>게시글 목록</h1>\n");

        sb.append("<ul>\n");
        for (Article article : articles) {
            sb.append("<li>");
            sb.append(article.getId());
            sb.append("|");
            sb.append(article.getCreateDate().toString().substring(3, 10));
            sb.append("|");
            sb.append(article.getModifyDate().toString().substring(3, 10));
            sb.append("|");
            sb.append(article.getAuthor());
            sb.append("|");
            sb.append(article.getTitle());
            sb.append("|");
            sb.append(article.getBody());
            sb.append("</li>\n");
        }
        sb.append("</ul>\n");


        return sb.toString();
    }

    @GetMapping("/article/list2")
    @ResponseBody
    public List<Article> showList2() {
        List<Article> articles = articleService.findAll();

        return articles;
    }
}
