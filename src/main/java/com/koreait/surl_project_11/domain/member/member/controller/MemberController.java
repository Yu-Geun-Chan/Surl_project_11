package com.koreait.surl_project_11.domain.member.member.controller;

import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.service.MemberService;
import com.koreait.surl_project_11.global.exceptions.GlobalException;
import com.koreait.surl_project_11.global.rsData.RsData;
import com.koreait.surl_project_11.standard.util.Ut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    @ResponseBody
    public RsData join(
            String username, String password, String nickname
    ) {
        if (Ut.str.isBlank(username)) {
//            return RsData.of("400-1", "username을 입력해");
            throw new GlobalException("400-1", "username을 입력해");
        }
        if (Ut.str.isBlank(password)) {
            throw new GlobalException("400-2", "password을 입력해");
        }
        if (Ut.str.isBlank(nickname)) {
            throw new GlobalException("400-3", "nickname을 입력해");
        }
        try {
            return  memberService.join(username, password, nickname);
        } catch (GlobalException e) {
            e.getRsData().getResultCode().equals("400-1");
            if (e.getRsData().getResultCode().equals("400-1")) {
               log.debug("이미 있는 회원입니다.");
            }
             return RsData.of("400-C", "커스텀 예외 메세지", Member.builder().build());
        }
    }

    @GetMapping("/testThrowIllegalArgumentException")
    @ResponseBody
    public RsData<Member> testThrowIllegalArgumentException() {
        throw new IllegalArgumentException("IllegalArgumentException");
    }
}
