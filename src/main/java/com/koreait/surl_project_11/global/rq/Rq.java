package com.koreait.surl_project_11.global.rq;

import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@RequiredArgsConstructor
public class Rq {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final MemberService memberService;

    @Getter
    @Setter
    private Member member;

//    public Member getMember() {
//        // 프록시 객체를 리턴한다. (객체를 만드는데 SQL이 작동하지 않아)
//        // 프록시 : 효율적
//        return memberService.getReferenceById(3L); // 3L은 user1 이니까
//    }

    public String getCurrentUrlPath() {
        return req.getRequestURI();
    }
    public void setStatusCode(int statusCode) {
        resp.setStatus(statusCode);
    }
}
