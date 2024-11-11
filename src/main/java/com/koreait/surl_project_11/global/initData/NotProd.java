package com.koreait.surl_project_11.global.initData;

import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class NotProd {

    // this를 통한 객체 내부에서의 메서드 호출은 @Transactional을 작동시키지 않는다.
    // 외부객체에 의한 메서드 호출은 @Transactional이 작동한다.
    // @Lazy, @Autowired 조합은 this의 외부 호출 모드 버전 self를 얻을 수 있다.
    // self를 통한 메서드 호출은 @Transactional을 작동 시킬 수 있다.
    @Lazy
    @Autowired
    private NotProd self;

    private final MemberService memberService;

    @Bean // 개발자가 new 하지 않아도 스프링부트가 직접 관리하는 객체 -> 실행될 때 자동으로!
    @Order(4)
    public ApplicationRunner initNotProd() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() {
        // 읽기전용 트랜잭션
        // Article 테이블에 데이터가 이미 존재한다면 종료
        // (TRUNCATE 개념 -> 테이블 초기화, AUTO_INCREMENT 적용됐던 것도 삭제.)
        log.debug("initAll started");

        if (memberService.count() > 0) return;

        // 쓰기전용 트랜잭션
        Member memberUser1 = memberService.findByUsername("user1").get();
        Member memberUser2 = memberService.findByUsername("user2").get();

        Member memberSystem = memberService.join("system", "1234", "시스템").getData();
        Member memberAdmin = memberService.join("admin", "1234", "관리자").getData();
        Member member1 = memberService.join("user1", "1234", "회원 1").getData();
        Member member2 = memberService.join("user2", "1234", "회원 2").getData();

    }
}

