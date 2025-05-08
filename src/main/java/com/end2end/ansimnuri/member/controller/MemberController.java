package com.end2end.ansimnuri.member.controller;

import com.end2end.ansimnuri.member.dto.MemberDTO;
import com.end2end.ansimnuri.member.service.MemberService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "유저 API", description = "유저 CRUD 기능을 가진 API")
@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {
    private final MemberService memberService;
@GetMapping("/login")
    public String login(@RequestBody MemberDTO dto, HttpServletRequest request) {
//        MemberDTO user = memberService.login(dto);
//        if (user == null) {
//            return "redirect:/";
//        }
//        return "redirect:/";
    return null;

    }
    @GetMapping("/checkId/{id}")
    public ResponseEntity<Map<String, Boolean>> checkId(@PathVariable String id) {
        boolean exists = memberService.isIdExist(id);
        Map<String, Boolean> result = new HashMap<>();
        result.put("exists", exists);
        return ResponseEntity.ok(result);
    }




}
