package com.end2end.ansimnuri.member.service;

import com.end2end.ansimnuri.member.dao.MemberDAO;
import com.end2end.ansimnuri.member.domain.entity.Member;
import com.end2end.ansimnuri.member.domain.repository.MemberRepository;
import com.end2end.ansimnuri.member.dto.LoginDTO;
import com.end2end.ansimnuri.member.dto.MemberDTO;
import com.end2end.ansimnuri.util.JWTUtil;
import com.end2end.ansimnuri.util.PasswordUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberDAO memberDAO;
    private final JWTUtil jwtUtil;
    private final PasswordUtil passwordUtil;

    @Transactional
    @Override
    public boolean isIdExist(String loginId) {
        return memberRepository.findByLoginId(loginId).orElse(null) != null;
    }

    @Transactional
    @Override
    public String login(LoginDTO dto){
        Member member = memberRepository
                .findByLoginId(dto.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("아이디가 일치하지 않습니다."));
        if(!passwordUtil.matches(dto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        List<String> roles = new ArrayList<>();
        roles.add(member.getRole().toString());

        return jwtUtil.createToken(member.getLoginId(), roles);
    }

    @Transactional
    @Override
    public void insert(MemberDTO memberDTO) {
        String password = passwordUtil.encodePassword(memberDTO.getPassword());
        memberDTO.setPassword(password);

        memberRepository.save(Member.of(memberDTO));
    }

    @Transactional
    @Override
    public void update(MemberDTO memberDTO) {
        Member member = memberRepository.findById(memberDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("%d에 해당하는 아이디를 가진 회원이 존재하지 않습니다.", memberDTO.getId())));
        member.update(memberDTO);
    }
}
