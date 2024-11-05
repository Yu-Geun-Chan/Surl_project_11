package com.koreait.surl_project_11;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SurlController {

    private List<Surl> surls;
    private long surlsLastid;

    @GetMapping("/add")
    @ResponseBody
    public String add(String body, String url) {
        Surl surl = Surl.builder()
                .id(++surlsLastid)
                .body(body)
                .url(url)
                .build();

        surls.add(surl);
        return surl.toString();
    }

}
