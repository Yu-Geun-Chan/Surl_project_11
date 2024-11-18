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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Slf4j
public class ApiV1MemberController {

    private final MemberService memberService;

    @AllArgsConstructor
    @Getter
    public static class MemberJoinRequestBody {
        @NotBlank // -> 써먹으려면 객체 선언부에 @Valid 어노테이션을 붙여야한다.
        private String username;
        @NotBlank
        private String password;
        @NotBlank
        private String nickname;
    }

    // POST api/v1/members/join
    @PostMapping("") // "join"을 쓰지않고 ""(빈문자열)로 해도 된다. why? -> POST니까
    public RsData<Member> join(
            @RequestBody @Valid MemberJoinRequestBody requestBody) { // @Valid : @NotBlank 쓰려면 해야됨
        return memberService.join(requestBody.username, requestBody.password, requestBody.nickname);
    }

    @GetMapping("/testThrowIllegalArgumentException")
    @ResponseBody
    public RsData<Member> testThrowIllegalArgumentException() {
        throw new IllegalArgumentException("IllegalArgumentException");
    }
}

