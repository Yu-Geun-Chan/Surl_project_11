package com.koreait.surl_project_11.domain.member.member.dto;

import com.koreait.surl_project_11.domain.member.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 보여주기용(Entity는 외부에 직접 노출시키면 안되기 때문)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberDto {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String username;
    private String nickname;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.createDate = member.getCreateDate();
        this.modifyDate = member.getModifyDate();
        this.username = member.getUsername();
        this.nickname = member.getNickname();
    }
}
