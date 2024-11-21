package com.koreait.surl_project_11.domain.surl.surl.controller;

import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.surl.surl.entity.Surl;
import com.koreait.surl_project_11.domain.surl.surl.service.SurlService;
import com.koreait.surl_project_11.global.exceptions.GlobalException;
import com.koreait.surl_project_11.global.rq.Rq;
import com.koreait.surl_project_11.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/surls")
@RequiredArgsConstructor
@Slf4j
public class ApiV1SurlController {

    private final Rq rq;
    private final SurlService surlService;

    @AllArgsConstructor
    @Getter
    public static class SurlAddReqBody {
        @NotBlank
        private String body;
        @NotBlank
        private String url;

    }
    // 응답 양식
    @AllArgsConstructor
    @Getter
    public static class SurlAddRespBody {
        private Surl item;
    }

    @PostMapping("")
    @ResponseBody
    public RsData<SurlAddRespBody> add(
            @RequestBody @Valid SurlAddReqBody reqBody
    ) {

        Member member = rq.getMember(); // 현재 브라우저로 로그인 한 회원 정보

        RsData<Surl> addRs = surlService.add(member, reqBody.body, reqBody.url);
        return addRs.newDataOf(
                new SurlAddRespBody(addRs.getData())
        );
    }

    // 응답 양식
    @AllArgsConstructor
    @Getter
    public static class SurlGetRespBody {
        private Surl item;
    }

    // /api/v1/surls/{id}
    // /api/v1/surls/1
    // /api/v1/surls?id=1
    @PostMapping("/{id}")
    @ResponseBody
    public RsData<SurlGetRespBody> get(
            @PathVariable long id
    ) {

        Surl surl = surlService.findById(id).orElseThrow(GlobalException.E404::new);

        return RsData.of(
                new SurlGetRespBody(surl)
        );
    }
}