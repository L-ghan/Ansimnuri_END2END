package com.end2end.ansimnuri.member.service;

import com.end2end.ansimnuri.member.dto.LoginDTO;
import com.end2end.ansimnuri.member.dto.LoginResultDTO;
import com.end2end.ansimnuri.member.dto.MemberDTO;
import com.end2end.ansimnuri.member.dto.MemberUpdateDTO;

public interface MemberService {
    boolean isIdExist(String id);
    LoginResultDTO login(LoginDTO dto);
    void insert(MemberDTO dto);
    void update(MemberDTO dto);
    boolean isNickNameExist(String nickName);
    MemberDTO selectByLoginId(String loginId);
    MemberDTO updateMyInformation(String loginId, MemberUpdateDTO dto);
    boolean checkEmail(String email);
    void changePassword(String loginId, String pw);
    String getPw(String loginId);
}
