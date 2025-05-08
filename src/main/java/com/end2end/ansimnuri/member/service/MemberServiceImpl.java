package com.end2end.ansimnuri.member.service;

import com.end2end.ansimnuri.member.dao.MemberDAO;
import com.end2end.ansimnuri.member.domain.entity.Member;
import com.end2end.ansimnuri.member.domain.repository.MemberRepository;
import com.end2end.ansimnuri.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberDAO memberDAO;


    public boolean isIdExist(String id) {
        return memberRepository.isIdExist(id);
    }
    public String login(MemberDTO dto){
//Member member = memberRepository.findBy(dto.getId())
//        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
   return null;
    }

}
