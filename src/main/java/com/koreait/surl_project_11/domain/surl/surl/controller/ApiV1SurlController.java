package com.koreait.surl_project_11.domain.surl.surl.controller;

import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.surl.surl.dto.SurlDto;
import com.koreait.surl_project_11.domain.surl.surl.entity.Surl;
import com.koreait.surl_project_11.domain.surl.surl.service.SurlService;
import com.koreait.surl_project_11.global.exceptions.GlobalException;
import com.koreait.surl_project_11.global.rq.Rq;
import com.koreait.surl_project_11.global.rsData.RsData;
import com.koreait.surl_project_11.standard.dto.Empty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/surls")
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
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
        private SurlDto item;
    }

    @PostMapping("")
    @ResponseBody
    @Transactional
    public RsData<SurlAddRespBody> add(
            @RequestBody @Valid SurlAddReqBody reqBody
    ) {

        Member member = rq.getMember(); // 현재 브라우저로 로그인 한 회원 정보

        RsData<Surl> addRs = surlService.add(member, reqBody.body, reqBody.url);
        return addRs.newDataOf(
                new SurlAddRespBody(
                        new SurlDto(addRs.getData())
                )
        );
    }

    // 응답 양식
    @AllArgsConstructor
    @Getter
    public static class SurlGetRespBody {
        private SurlDto item;
    }

    // /api/v1/surls/{id}
    // /api/v1/surls/1
    // /api/v1/surls?id=1
    @GetMapping("/{id}")
    // @Transactional 안한 이유 : 컨트롤러 자체에 @Transactional(readOnly = true)가 붙어있어서 적용되니까.
    public RsData<SurlGetRespBody> get(
            @PathVariable long id
    ) {

        Surl surl = surlService.findById(id).orElseThrow(GlobalException.E404::new);

        Member member = rq.getMember();

         if(!surl.getAuthor().equals(member)) {
//        if(surl.getAuthor().getId() != member.getId()) {
            // 403 error는 권한 거부
            throw new GlobalException("403-1", "권한이 없습니다.");
        }

        return RsData.of(
                new SurlGetRespBody(
                        new SurlDto(surl)
                )
        );
    }

    // 응답 양식
    @AllArgsConstructor
    @Getter
    public static class SurlGetItemsRespBody {
        private List<SurlDto> item;
    }

    @GetMapping("")
    // @Transactional 안한 이유 : 컨트롤러 자체에 @Transactional(readOnly = true)가 붙어있어서 적용되니까.
    public RsData<SurlGetItemsRespBody> getItems() {
        Member member = rq.getMember();

        // Page
        // QueryDSL

        List<Surl> surls = surlService.findByAuthorOrderByIdDesc(member);

        return RsData.of(
                new SurlGetItemsRespBody(
                        surls.stream()
                                .map(SurlDto::new)
                                .toList()
                )
        );
    }

    @DeleteMapping("/{id}")
    @Transactional
    // 딱히 리턴할게 없어! -> 제네릭안에 Empty
    public RsData<Empty> delete(
            @PathVariable long id
    ) {
        Surl surl = surlService.findById(id).orElseThrow(GlobalException.E404::new);

        Member member = rq.getMember();

        if(!surl.getAuthor().equals(member)) {
            // 403 error는 권한 거부
            throw new GlobalException("403-1", "권한이 없습니다.");
        }

        surlService.delete(surl);

        return RsData.OK;
    }

    @AllArgsConstructor
    @Getter
    public static class SurlModifyReqBody {
        @NotBlank
        private String body;
        @NotBlank
        private String url;

    }

    // 응답 양식
    @AllArgsConstructor
    @Getter
    public static class SurlModifyRespBody {
        private SurlDto item;
    }

    @PutMapping("/{id}")
    @Transactional
    public RsData<SurlModifyRespBody> modify(
            @PathVariable long id,
            @RequestBody @Valid SurlModifyReqBody reqBody
    ) {
        Surl surl = surlService.findById(id).orElseThrow(GlobalException.E404::new);

        Member member = rq.getMember();

        if(!surl.getAuthor().equals(member)) {
            // 403 error는 권한 거부
            throw new GlobalException("403-1", "권한이 없습니다.");
        }

        RsData<Surl> modifyRs = surlService.modify(surl, reqBody.body, reqBody.url);

        return modifyRs.newDataOf(
                new SurlModifyRespBody(
                        new SurlDto(modifyRs.getData())
                )
        );
    }
}