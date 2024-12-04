package com.koreait.surl_project_11.global.rq;

import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.service.MemberService;
import com.koreait.surl_project_11.global.app.AppConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public Member getMember() {
        if (member != null) return member; // 메모리 캐싱

        // CustomAuthenticationFilter의 인증을 뚫고 온거니까 믿어도 된다.
        long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());

        member = memberService.findById(id).get();

        return member;
    }

    public String getCurrentUrlPath() {
        return req.getRequestURI();
    }

    public void setStatusCode(int statusCode) {
        resp.setStatus(statusCode);
    }

    public String getCookieValue(String cookieName, String defaultValue) {
        if (req.getCookies() == null) return defaultValue;

        return Arrays.stream(req.getCookies()) // 쿠키 배열을 스트림으로 변환
                .filter(cookie -> cookie.getName().equals(cookieName))// 쿠키의 이름이 매개변수로 쓰이게
                .findFirst() // 첫 번째 요소
                .map(Cookie::getValue) // 존재하면 쿠키 값으로 매핑
                .orElse(defaultValue); // 없으면 기본 값
    }

    public void removeCookie(String name) {
        ResponseCookie cookie = ResponseCookie.from(name)
                .path("/")
                .maxAge(0)
                .domain(getSiteCookieDomain())
                .sameSite("Strict")
                .secure(true)
                .httpOnly(true)
                .build();
        resp.addHeader("Set-Cookie", cookie.toString());
    }

    public void setCookie(String name, String value) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path("/")
                .maxAge(60 * 60 * 24 * 365 * 10)
                .domain(getSiteCookieDomain())
                .sameSite("Strict")
                .secure(true)
                .httpOnly(true)
                .build();
        resp.addHeader("Set-Cookie", cookie.toString());
    }

    public String getSiteCookieDomain() {
        String cookieDomain = AppConfig.getSiteCookieDomain();

        if (cookieDomain.equals("localhost")) return cookieDomain;

        return "." + cookieDomain;
    }
}