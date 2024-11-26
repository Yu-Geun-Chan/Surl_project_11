package com.koreait.surl_project_11.global.rq;

import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.service.MemberService;
import com.koreait.surl_project_11.global.exceptions.GlobalException;
import com.koreait.surl_project_11.standard.util.Ut;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Arrays;

@Component
@RequestScope
@RequiredArgsConstructor
public class Rq {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final MemberService memberService;

    private Member member;

    //   @Getter
    //   @Setter
    //  private Member member;

    public Member getMember() {
        if (member != null) return member; // 메모리 캐싱

        String actorUsername = getCookieValue("actorUsername", null);
        String actorPassword = getCookieValue("actorPassword", null);

//        String actorUsername = req.getParameter("actorUsername");
//        String actorPassword = req.getParameter("actorPassword");

        if (actorUsername == null || actorPassword == null) {
            String authorization = req.getHeader("Authorization");
            if (authorization != null) {
                authorization = authorization.substring("bearer ".length());
                String[] authorizationBits = authorization.split(" ", 2);
                actorUsername = authorizationBits[0];
                actorPassword = authorizationBits.length == 2 ? authorizationBits[1] : null;
            }
        }

//        if (actorUsername == null) actorUsername = req.getHeader("actorUsername");
//        if (actorPassword == null) actorPassword = req.getHeader("actorPassword");

        if (Ut.str.isBlank(actorUsername)) throw new GlobalException("401-1", "인증정보(아이디) 입력해줘");
        if (Ut.str.isBlank(actorPassword)) throw new GlobalException("401-2", "인증정보(비밀번호) 입력해줘");

        Member loginedMember = memberService.findByUsername(actorUsername).orElseThrow(() -> new GlobalException("403-3", "해당 회원은 없어"));
        if (!loginedMember.getPassword().equals(actorPassword)) throw new GlobalException("403-4", "비밀번호 틀림");

        member = loginedMember;

        return loginedMember;
    }

    private String getCookieValue(String cookieName, String defaultValue) {
        return Arrays.stream(req.getCookies()) // 쿠키 배열을 스트림으로 변환
                .filter(cookie -> cookie.getName().equals(cookieName))// 쿠키의 이름이 매개변수로 쓰이게
                .findFirst() // 첫 번째 요소
                .map(Cookie::getValue) // 존재하면 쿠키 값으로 매핑
                .orElse(defaultValue); // 없으면 기본 값
    }

    public String getCurrentUrlPath() {
        return req.getRequestURI();
    }

    public void setStatusCode(int statusCode) {
        resp.setStatus(statusCode);
    }
}