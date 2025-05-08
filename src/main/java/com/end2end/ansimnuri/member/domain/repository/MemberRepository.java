package com.end2end.ansimnuri.member.domain.repository;

import com.end2end.ansimnuri.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean isIdExist(String id);
    Member findByIdAndPassword(String id, String password);

}
