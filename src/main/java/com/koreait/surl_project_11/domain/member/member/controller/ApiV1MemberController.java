package com.koreait.surl_project_11.domain.member.member.controller;

import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.service.MemberService;
import com.koreait.surl_project_11.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Slf4j
public class ApiV1MemberController {

    private final MemberService memberService;

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
        Member item;
    }

    // POST api/v1/members/join
    @PostMapping("") // "join"을 쓰지않고 ""(빈문자열)로 해도 된다. why? -> POST니까
    public RsData<MemberJoinRespBody> join(
            @RequestBody @Valid MemberJoinReqBody requestBody // @Valid : @NotBlank 쓰려면 해야됨
    ) {
        RsData<Member> joinRs = memberService.join(requestBody.username, requestBody.password, requestBody.nickname);

        return joinRs.newDataOf(
                new MemberJoinRespBody(joinRs.getData())
        );
    }
}

