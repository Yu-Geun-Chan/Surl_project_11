package com.koreait.surl_project_11.domain.member.member.controller;

import com.koreait.surl_project_11.domain.auth.service.AuthService;
import com.koreait.surl_project_11.domain.auth.service.AuthTokenService;
import com.koreait.surl_project_11.domain.member.member.dto.MemberDto;
import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.service.MemberService;
import com.koreait.surl_project_11.global.app.AppConfig;
import com.koreait.surl_project_11.global.exceptions.GlobalException;
import com.koreait.surl_project_11.global.rq.Rq;
import com.koreait.surl_project_11.global.rsData.RsData;
import com.koreait.surl_project_11.standard.dto.Empty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
//@RequestMapping(value = "/api/v1/members", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@Tag(name = "ApiMemberController", description = "회원 CRUD 컨트롤러")
public class ApiV1MemberController {

    private final MemberService memberService;
    private final Rq rq;
    private final AuthService authService;
    private final AuthTokenService authTokenService;

    // POST api/v1/members/join
    @PostMapping("") // "join"을 쓰지않고 ""(빈문자열)로 해도 된다. why? -> POST니까
    @Transactional
    @Operation(summary = "회원가입")
    public RsData<MemberJoinRespBody> join(
            @RequestBody @Valid MemberJoinReqBody requestBody // @Valid : @NotBlank 쓰려면 해야됨
    ) {
        RsData<Member> joinRs = memberService.join(requestBody.username, requestBody.password, requestBody.nickname);

        return joinRs.newDataOf(
                new MemberJoinRespBody(
                        new MemberDto(joinRs.getData())
                )
        );
    }

    @PostMapping("/login")
    @Transactional
    @Operation(summary = "로그인", description = "성공하면 accessToken, refreshToken 쿠키가 생성됨")
    public RsData<MemberLoginRespBody> login(
            @RequestBody @Valid MemberLoginReqBody requestBody
    ) {
        Member member = memberService.findByUsername(requestBody.username).orElseThrow(() -> new GlobalException("401-1", "해당 회원은 없다"));
        if (!memberService.matchPassword(requestBody.password, member.getPassword())) {
            throw new GlobalException("401-2", "비번 틀림");
        }

        String accessToken = authTokenService.genToken(member, AppConfig.getAccessTokenExpirationSec());

        rq.setCookie("accessToken", accessToken);
        rq.setCookie("refreshToken", member.getRefreshToken());

        return RsData.of(
                "200-1", "로그인 성공", new MemberLoginRespBody(new MemberDto(member))
        );
    }

    @DeleteMapping("/logout")
    @Transactional
    @Operation(summary = "로그아웃")
    public RsData<Empty> logout() {
        // 쿠키 삭제
        rq.removeCookie("actorUsername");
        rq.removeCookie("actorPassword");
        return RsData.OK;
    }

    @AllArgsConstructor
    @Getter
    public static class MemberJoinReqBody {
        @NotBlank // -> 써먹으려면 객체 선언부에 @Valid 어노테이션을 붙여야한다.
        private String username;
        @NotBlank
        private String password;
        @NotBlank
        private String nickname;
    }

    // 응답 양식
    @AllArgsConstructor
    @Getter
    public static class MemberJoinRespBody {
        MemberDto item;
    }

    @AllArgsConstructor
    @Getter
    public static class MemberLoginReqBody {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @AllArgsConstructor
    @Getter
    public static class MemberLoginRespBody {
        MemberDto item;
    }
}

