package com.end2end.ansimnuri.member.service;

import com.end2end.ansimnuri.member.dao.MemberDAO;
import com.end2end.ansimnuri.member.domain.entity.Member;
import com.end2end.ansimnuri.member.domain.repository.MemberRepository;
import com.end2end.ansimnuri.member.dto.LoginDTO;
import com.end2end.ansimnuri.member.dto.MemberDTO;
import com.end2end.ansimnuri.member.dto.MemberUpdateDTO;
import com.end2end.ansimnuri.util.JWTUtil;
import com.end2end.ansimnuri.util.PasswordUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberDAO memberDAO;
    private final JWTUtil jwtUtil;
    private final PasswordUtil passwordUtil;

    @Override
    public boolean isIdExist(String loginId) {
        return memberRepository.findByLoginId(loginId).orElse(null) != null;
    }

    @Transactional
    @Override
    public String login(LoginDTO dto) {
        Member member = memberRepository
                .findByLoginId(dto.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("아이디가 일치하지 않습니다."));
        if (!passwordUtil.matches(dto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        List<String> roles = new ArrayList<>();
        roles.add(member.getRole().getRole());


        return jwtUtil.createToken(member.getLoginId(), roles);
    }

    @Transactional
    @Override
    public boolean isNickNameExist(String nickName) {
        return memberRepository.findByNickname(nickName).orElse(null) != null;
    }

    @Transactional
    @Override
    public void insert(MemberDTO memberDTO) {
        String password = passwordUtil.encodePassword(memberDTO.getPassword());
        memberDTO.setPassword(password);
       //패스워드 암호화 처리
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

    @Override
    public MemberDTO selectByLoginId(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));

        return MemberDTO.of(member);
    }

    public MemberDTO updateMyInformation(String loginId, MemberUpdateDTO dto) {
        Member member = memberRepository.findByLoginId(loginId)

                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if (!member.getNickname().equals(dto.getNickname())) {

            Optional<Member> exists = memberRepository.findByNickname(dto.getNickname());
            if (exists.isPresent()) {
                throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
            }


        }
        member.update(
                MemberDTO.builder().nickname(dto.getNickname()
                        ).email(member.getEmail())
                        .postcode(member.getPostcode())
                        .address(member.getAddress())
                        .detailAddress(member.getDetailAddress())
                        .build());

        memberRepository.save(member);
        return MemberDTO.of(member);
    }
    @Transactional
    @Override
    public List<MemberDTO> getAllMembers() {
       List<Member> members = memberRepository.findAll().stream()
               .filter(member -> member.getBlockList().isEmpty())
               .toList();

       List<MemberDTO> memberDTOs = new ArrayList<>();
       for(Member member : members){
       MemberDTO memberDTO = MemberDTO.builder()
               .id(member.getId())
               .nickname(member.getNickname())
               .loginId(member.getLoginId())
               .email(member.getEmail())
               .regDate(member.getRegDt())
               .nickname(member.getNickname())
               .address(member.getAddress()).build();
           memberDTOs.add(memberDTO);
       }
        return memberDTOs;
    }



}
