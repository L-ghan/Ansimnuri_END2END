package com.end2end.ansimnuri.user.domain.repository;

import com.end2end.ansimnuri.user.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
