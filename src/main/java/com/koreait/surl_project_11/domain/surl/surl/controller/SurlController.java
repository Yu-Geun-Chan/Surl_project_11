package com.koreait.surl_project_11.domain.surl.surl.controller;

import com.koreait.surl_project_11.domain.surl.surl.entity.Surl;
import com.koreait.surl_project_11.domain.surl.surl.service.SurlService;
import com.koreait.surl_project_11.global.exceptions.GlobalException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
@Slf4j
@Tag(name = "SurlController", description = "Surl 컨트롤러")
public class SurlController {

    private final SurlService surlService;

    @GetMapping("/g/{id}")
    @Transactional
    @Operation(summary = "원본 URL로 리다이렉트")
    public String go(
            @PathVariable long id
    ) {
        Surl surl = surlService.findById(id).orElseThrow(GlobalException.E404::new);

        surlService.increaseCount(surl);

        return "redirect:" + surl.getUrl();
    }
}
