package com.koreait.surl_project_11.domain.member.member.controller;

import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.service.MemberService;
import com.koreait.surl_project_11.global.exceptions.GlobalException;
import com.koreait.surl_project_11.global.rsData.RsData;
import com.koreait.surl_project_11.standard.util.Ut;
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
        private String username;
        private String password;
        private String nickname;
    }

    // POST api/v1/members/join
    @PostMapping("") // "join"을 쓰지않고 ""(빈문자열)로 해도 된다. why? -> POST니까
    public RsData<Member> join(
            @RequestBody MemberJoinRequestBody requestBody) {
        if (Ut.str.isBlank(requestBody.username)) {
            throw new GlobalException("400-1", "username을 입력해");
        }
        if (Ut.str.isBlank(requestBody.password)) {
            throw new GlobalException("400-2", "password을 입력해");
        }
        if (Ut.str.isBlank(requestBody.nickname)) {
            throw new GlobalException("400-3", "nickname을 입력해");
        }
        return memberService.join(requestBody.username, requestBody.password, requestBody.nickname);
    }

    @GetMapping("/testThrowIllegalArgumentException")
    @ResponseBody
    public RsData<Member> testThrowIllegalArgumentException() {
        throw new IllegalArgumentException("IllegalArgumentException");
    }
}
