package com.koreait.surl_project_11.domain.surl.surl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.global.jpa.entity.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class Surl extends BaseTime {

    // surl 입장에선 다른 곳에서 가져온거니까
    // Many : surl, One : member -> 하나의 회원이 여러개를 등록할 수 있으니까.
    @ManyToOne
    @JsonIgnore
    private Member author;

    private String body;
    private String url;
    @Setter(AccessLevel.NONE)
    private long count;

    public void increaseCount() {
        count++;
    }
}
