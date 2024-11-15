package com.koreait.surl_project_11.domain.member.member.controller;

import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.service.MemberService;
import com.koreait.surl_project_11.global.exceptions.GlobalException;
import com.koreait.surl_project_11.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    @ResponseBody
    public RsData join(
            String username, String password, String nickname
    ) {
        if (username == null || username.isBlank()) {
//            return RsData.of("400-1", "username을 입력해");
            throw new GlobalException("400-1", "username을 입력해");
        }
        if (password == null || password.isBlank()) {
            throw new GlobalException("400-2", "password을 입력해");
        }
        if (nickname == null || nickname.isBlank()) {
            throw new GlobalException("400-3", "nickname을 입력해");
        }
        RsData<Member> joinRs = memberService.join(username, password, nickname);
        return joinRs;
    }
}
