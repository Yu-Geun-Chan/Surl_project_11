package com.koreait.surl_project_11.domain.article.article.entity;

import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.global.jpa.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity // 이거 가지고 article 테이블 만들거임.
@Builder
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED) // @Entity 쓸거면 필수
@AllArgsConstructor(access = PROTECTED) // @Builder 쓸거면 필수
public class Article extends BaseTime {
    private String title;
    @Column(columnDefinition = "TEXT")  // 타입을 TEXT로 하겠다.
    private String body;

    // article 입장에선 다른 곳에서 가져온거니까
    // Many : article, One : member -> 하나의 회원이 여러개의 글을 쓸 수 있으니까.
    @ManyToOne
    private Member author;
}
