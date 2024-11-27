package com.koreait.surl_project_11.domain.member.member.repository;

import com.koreait.surl_project_11.domain.member.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 제네릭이 <Member, Long>인 이유 : Member의 PK가 id인데 id의 타입이 long 이므로
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    Optional<Member> findByApiKey(String apiKey);
}
