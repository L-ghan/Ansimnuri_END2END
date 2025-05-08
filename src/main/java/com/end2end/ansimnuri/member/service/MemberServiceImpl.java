package com.end2end.ansimnuri.member.service;

import com.end2end.ansimnuri.member.dao.MemberDAO;
import com.end2end.ansimnuri.member.domain.entity.Member;
import com.end2end.ansimnuri.member.domain.repository.MemberRepository;
import com.end2end.ansimnuri.member.dto.MemberDTO;
import com.end2end.ansimnuri.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberDAO memberDAO;
    private final JWTUtil jwtUtil;

    @Override
    public boolean isIdExist(String loginId) {
        return memberRepository.findByLoginId(loginId) != null;
    }

    @Override
    public String login(MemberDTO dto){
        Member member = memberRepository
                .findByLoginIdAndPassword(dto.getLoginId(), dto.getPassword())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 아이디, 비밀번호입니다."));
        List<String> roles = new ArrayList<>();
        roles.add(member.getRole().toString());

        return jwtUtil.createToken(member.getLoginId(), roles);
    }

    @Override
    public void insert(MemberDTO memberDTO) {
        memberRepository.save(Member.of(memberDTO));
    }

    @Override
    public void update(MemberDTO memberDTO) {
        Member member = memberRepository.findByLoginId(memberDTO.getLoginId());
        member.update(memberDTO);
    }
}
