package com.koreait.surl_project_11.domain.member.member.service;

import com.koreait.surl_project_11.domain.member.member.entity.Member;
import com.koreait.surl_project_11.domain.member.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.koreait.surl_project_11.global.exceptions.GlobalException;
import com.koreait.surl_project_11.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
// @Transactional : public 메서드에는 붙는게 관례, private 메서드에는 적용되지 않는다.
// readOnly = true : SELECT만 할거 같을 경우
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Transactional()
    public RsData<Member> join(String username, String password, String nickname) {
//      ==v1==
//      boolean present = findByUsername(username).isPresent();
//      if (present) {
//          throw new GlobalException("400-1", "이미 존재하는 아이디입니다.")
//      }
//      ==v2==
        findByUsername(username).ifPresent(m -> {
            throw new GlobalException("400-1", "이미 존재하는 아이디입니다.");
        });

        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .build();

        memberRepository.save(member);

        return RsData.of("회원가입이 완료되었습니다.", member);
    }

    // findById랑 똑같다고 생각해라.
    public Member getReferenceById(long id) {
        return memberRepository.getReferenceById(id);
    }

    public long count() {
        return memberRepository.count();
    }

    public boolean matchPassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }

    public Optional<Member> findById(long id) {
        return memberRepository.findById(id);
    }
}
