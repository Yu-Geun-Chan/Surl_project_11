package com.koreait.surl_project_11.domain.article.article.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity // 이거 가지고 article 테이블 만들거임.
@Builder
@Getter
@Setter
@NoArgsConstructor // @Entity 쓸거면 필수
@AllArgsConstructor // @Builder 쓸거면 필수
@EntityListeners(AuditingEntityListener.class) // @CreatedDate, @LastModifiedDate 이거 쓰려면 필요
public class Article {

    @Id // 이거 PK(Primary Key)라고 알려주는 것.
    @GeneratedValue(strategy = IDENTITY) // AUTO_INCREMENT 할거임.
    private long id;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime modifyDate;
    private String title;
    @Column(columnDefinition = "TEXT")  // 타입을 TEXT로 하겠다. nullable = false : NOT NULL로 하겠다.
    private String body;

}
