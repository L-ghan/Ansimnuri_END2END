package com.end2end.ansimnuri.member.domain.repository;

import com.end2end.ansimnuri.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);

    Optional<Member> findByNickname(String nickName);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByKakaoId(String kakaoId);
}