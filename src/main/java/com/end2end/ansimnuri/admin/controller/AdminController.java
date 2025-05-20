package com.end2end.ansimnuri.admin.controller;

import com.end2end.ansimnuri.member.dto.MemberDTO;
import com.end2end.ansimnuri.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/admin")
@RestController
public class AdminController {

    private final MemberService memberService;

    @GetMapping
    public List<MemberDTO> getAllMembers(){
        return memberService.getAllMembers();
    }
}
