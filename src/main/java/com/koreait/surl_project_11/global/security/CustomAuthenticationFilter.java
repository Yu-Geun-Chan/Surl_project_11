package com.koreait.surl_project_11.global.security;

import com.koreait.surl_project_11.domain.auth.service.AuthTokenService;
import com.koreait.surl_project_11.domain.member.member.service.MemberService;
import com.koreait.surl_project_11.global.rq.Rq;
import com.koreait.surl_project_11.standard.util.Ut;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final MemberService memberService;
    private final Rq rq;
    private final AuthTokenService authTokenService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) {
        String accessToken = rq.getCookieValue("accessToken", null);
        if (accessToken == null) {
            String authorization = req.getHeader("Authorization");
            if (accessToken != null) {
                accessToken = authorization.substring("bearer ".length());
            }
        }
        if (Ut.str.isBlank(accessToken)) {
            filterChain.doFilter(req, resp);
            return;
        }

        if (!authTokenService.validateToken(accessToken)) {
            filterChain.doFilter(req, resp);
            return;
        }

        Map<String,Object> accessTokenData = authTokenService.getDataFrom(accessToken);

        long id = (int) accessTokenData.get("id");

        // 여기까지 왔다는건 인증을 뚫었다는 것.
        // User : Spring Security가 이해할 수 있는 형태의 Member
        User user = new User(id + "", "", List.of());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(req, resp); // Filter를 종료하고 다음턴으로 넘긴다.
    }
}