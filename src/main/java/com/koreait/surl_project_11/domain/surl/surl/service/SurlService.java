package com.koreait.surl_project_11.domain.surl.surl.service;

import com.koreait.surl_project_11.domain.surl.surl.entity.Surl;
import com.koreait.surl_project_11.domain.surl.surl.repository.SurlRepository;
import com.koreait.surl_project_11.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SurlService { // 비즈니스 로직 처리 담당

    private final SurlRepository surlRepository;

    public List<Surl> findAll() {
        return surlRepository.findAll();
    }

    public RsData<Surl> add(String body, String url) {
        Surl surl = Surl.builder()
                .body(body)
                .url(url).build();

        surlRepository.save(surl);

        return RsData.of("%d번 URL이 등록되었습니다.".formatted(surl.getId()), surl);
    }

    public Optional<Surl> findById(long id) {
        return surlRepository.findById(id);
    }

    public void increaseCount(Surl surl) {
        surl.increaseCount();
    }
}
