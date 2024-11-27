package com.koreait.surl_project_11.domain.member.member.entity;

import com.koreait.surl_project_11.global.jpa.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity // 이거 가지고 member 테이블 만들거임.
@Builder
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED) // @Entity 쓸거면 필수
@AllArgsConstructor(access = PROTECTED) // @Builder 쓸거면 필수
public class Member extends BaseTime {
    @Column(unique = true) // Unique
    private String username;
    private String password;
    private String nickname;
    @Column(unique = true)
    private String apiKey;

    public String getName() {
        return nickname;
    }
}
