package com.koreait.surl_project_11.global.security;

import com.koreait.surl_project_11.domain.auth.service.AuthTokenService;
import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.service.MemberService;
import com.koreait.surl_project_11.global.app.AppConfig;
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
        String refreshToken = rq.getCookieValue("refreshToken", null);
        if (accessToken == null || refreshToken == null) {
            String authorization = req.getHeader("Authorization");
            if (accessToken != null) {
                String[] authorizationBits = authorization.substring("bearer ".length()).split(" ", 2);
                if (authorizationBits.length == 2) {
                    accessToken = authorizationBits[0];
                    refreshToken = authorizationBits[1];
                }
            }
        }
        if (Ut.str.isBlank(accessToken) || Ut.str.isBlank(refreshToken)) {
            filterChain.doFilter(req, resp);
            return;
        }


        // 기존의 accessToken이 만료됐다면 DB에서 refreshToken과 일치하는 회원 한명을 가져와.
        if (!authTokenService.validateToken(accessToken)) {
            Member member = memberService.findByRefreshToken(refreshToken).orElse(null);

            // 일치하는 회원이 없다면 null이니까 그냥 끝내.
            if (member == null) {
                filterChain.doFilter(req, resp);
                return;
            }

            // 새로운 accessToken을 만들어서 Cookie에 저장해.
            String newAccessToken = authTokenService.genToken(member, AppConfig.getAccessTokenExpirationSec());
            rq.setCookie("accessToken", newAccessToken);
            log.debug("accessToken renewed : {}", newAccessToken);

            // 그러고 새로 만들어진 accessToken 값을 기존의 accessToken이 담겨있던 변수에 덮어씌워.
            accessToken = newAccessToken;
        }

        Map<String, Object> accessTokenData = authTokenService.getDataFrom(accessToken);

        // 해당하는 회원의 id를 가져와서 id라는 변수에 저장해.
        // accessTokenData.get("id")는 Integer 타입이니까 형변환을 해야해.
        long id = (int) accessTokenData.get("id");

        // 여기까지 왔다는건 인증을 뚫었다는 것.
        // User : Spring Security가 이해할 수 있는 형태의 Member
        User user = new User(id + "", "", List.of());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(req, resp); // Filter를 종료하고 다음턴으로 넘긴다.
    }
}