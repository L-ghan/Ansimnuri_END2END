package com.end2end.ansimnuri.member.service;

import com.end2end.ansimnuri.member.dto.LoginDTO;
import com.end2end.ansimnuri.member.dto.MemberDTO;

public interface MemberService {
    boolean isIdExist(String id);
    String login(LoginDTO dto);
    void insert(MemberDTO dto);
    void update(MemberDTO dto);
    boolean isNickNameExist(String nickName);

}
