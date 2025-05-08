package com.end2end.ansimnuri.member.controller;

import com.end2end.ansimnuri.member.dto.MemberDTO;
import com.end2end.ansimnuri.member.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저 API", description = "유저 CRUD 기능을 가진 API")
@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public ResponseEntity<String> login(MemberDTO dto) {
        return ResponseEntity.ok(memberService.login(dto));
    }

    @GetMapping("/checkId/{loginId}")
    public ResponseEntity<Boolean> checkId(@PathVariable String loginId) {
        return ResponseEntity.ok(memberService.isIdExist(loginId));
    }
}
