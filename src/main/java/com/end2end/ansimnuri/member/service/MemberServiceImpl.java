package com.end2end.ansimnuri.member.service;

import com.end2end.ansimnuri.member.dao.MemberDAO;
import com.end2end.ansimnuri.member.domain.entity.Member;
import com.end2end.ansimnuri.member.domain.repository.MemberRepository;
import com.end2end.ansimnuri.member.dto.LoginDTO;
import com.end2end.ansimnuri.member.dto.LoginResultDTO;
import com.end2end.ansimnuri.member.dto.MemberDTO;
import com.end2end.ansimnuri.member.dto.MemberUpdateDTO;
import com.end2end.ansimnuri.util.JWTUtil;
import com.end2end.ansimnuri.util.PasswordUtil;
import com.end2end.ansimnuri.util.enums.Roles;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;
    private final PasswordUtil passwordUtil;
    private final PasswordEncoder passwordEncoder;


    @Override
    public boolean isIdExist(String loginId) {
        return memberRepository.findByLoginId(loginId).orElse(null) != null;
    }

    @Transactional
    @Override
    public LoginResultDTO login(LoginDTO dto) {
        Member member = memberRepository
                .findByLoginId(dto.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("아이디가 일치하지 않습니다."));
        if (!passwordUtil.matches(dto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        List<String> roles = new ArrayList<>();
        roles.add(member.getRole().getRole());

        return LoginResultDTO.builder()
                .id(member.getId())
                .token(jwtUtil.createToken(member.getLoginId(), roles))
                .build();
    }
    @Override
    @Transactional
    public LoginResultDTO registerOAuthIfNeeded(String kakaoId, String nickname) {
        Optional<Member> optional = memberRepository.findByLoginId(kakaoId);
        Member member;

        if (optional.isEmpty()) {
            member = Member.builder()
                    .loginId(kakaoId)
                    .kakaoId(kakaoId)
                    .nickname(nickname + "_" + UUID.randomUUID().toString().substring(0, 5))

                    .email(kakaoId + "@kakao.oauth")
                    .password(passwordUtil.encodePassword("oauth"))
                    .address("간편가입")
                    .detailAddress("없음")
                    .postcode("00000")
                    .role(Roles.USER)
                    .build();

            memberRepository.save(member);
        } else {
            member = optional.get();
        }

        List<String> roles = new ArrayList<>();
        roles.add(member.getRole().getRole());

        return LoginResultDTO.builder()
                .id(member.getId())
                .token(jwtUtil.createToken(member.getLoginId(), roles))
                .build();
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

    @Override
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
@Override
public void register(MemberDTO dto){

    String password =passwordEncoder.encode(dto.getPassword());
dto.setPassword(password);
System.out.println("암호화 비밀번호"+dto.getPassword());
    memberRepository.save(Member.of(dto));
}
    @Override
    public void changePassword(String loginId, String pw) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("유저 없음"));

        String password = passwordEncoder.encode(pw);// 비밀번호 암호화 작업
        member.change(password);
        memberRepository.save(member);
    }
    @Override
    public void changeLoginIdByemail(String email, String loginId) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("이메일 없음"));

        member.changeLoginId(loginId);
        memberRepository.save(member);
    }

    @Override
    public boolean checkEmail(String email) {

        return memberRepository.findByEmail(email).orElse(null) != null;
    }

    @Override
    public String getPw(String loginId) {

        Optional<Member> member = memberRepository.findByLoginId(loginId);
        return member.get().getPassword();
    }
    @Transactional
    @Override
    public void deleteByLoginId(String loginId){
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 회원이 존재하지 않습니다."));
        memberRepository.delete(member);
    }


}
