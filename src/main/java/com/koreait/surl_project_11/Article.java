package com.koreait.surl_project_11;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity // 이거 가지고 article 테이블 만들거임.
public class Article {

    @Id // 이거 PK(Primary Key)라고 알려주는 것.
    @GeneratedValue(strategy = IDENTITY) // AUTO_INCREMENT 할거임.
    private long id;
    private String title;
    private String body;

}
