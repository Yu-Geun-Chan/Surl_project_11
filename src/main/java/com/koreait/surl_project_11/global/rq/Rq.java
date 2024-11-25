package com.koreait.surl_project_11.global.rq;

import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.service.MemberService;
import com.koreait.surl_project_11.global.exceptions.GlobalException;
import com.koreait.surl_project_11.standard.util.Ut;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@RequiredArgsConstructor
public class Rq {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final MemberService memberService;

//    @Getter
//    @Setter
//    private Member member;

    public Member getMember() {
        // 프록시 객체를 리턴한다. (객체를 만드는데 SQL이 작동하지 않아)
        // 프록시 : 효율적
        String actorUsername = req.getParameter("actorUsername");

        if (Ut.str.isBlank(actorUsername)) throw new GlobalException("401-1", "인증정보 입력해줘");

        Member loginedMember = memberService.findByUsername(actorUsername).orElseThrow(() -> new GlobalException("401-2", "인증 정보가 올바르지 않아"));

        return loginedMember;
    }

    public String getCurrentUrlPath() {
        return req.getRequestURI();
    }
    public void setStatusCode(int statusCode) {
        resp.setStatus(statusCode);
    }
}
