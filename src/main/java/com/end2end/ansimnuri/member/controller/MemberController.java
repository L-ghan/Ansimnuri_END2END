package com.end2end.ansimnuri.member.controller;

import com.end2end.ansimnuri.member.dto.LoginDTO;
import com.end2end.ansimnuri.member.dto.MemberDTO;
import com.end2end.ansimnuri.member.dto.MemberUpdateDTO;
import com.end2end.ansimnuri.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 API", description = "유저 CRUD 기능을 가진 API")
@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "로그인 API", description = "아이디, 비밀번호를 받아서 로그인을 실행한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "아이디 또는, 비밀번호가 일치하지 않습니다.")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO dto) {
        System.out.println("dto: " + dto);
        return ResponseEntity.ok(memberService.login(dto));
    }


    @Operation(summary = "아이디 중복 체크 API", description = "아이디의 중복 여부를 확인해서 boolean값으로 반환한다.")
    @ApiResponse(responseCode = "200", description = "정상 작동입니다.")
    @GetMapping("/checkId/{loginId}")
    public ResponseEntity<Boolean> checkId(
            @Parameter(description = "로그인 아이디")
            @PathVariable String loginId) {
        return ResponseEntity.ok(memberService.isIdExist(loginId));
    }

    @Operation(summary = "닉네임 중복 체크 API", description = "닉네임의 중복 여부를 확인해서 boolean값으로 반환한다.")
    @ApiResponse(responseCode = "200", description = "정상 작동입니다.")
    @GetMapping("/checkNickName/{nickName}")
    public ResponseEntity<Boolean> checkNickName(
            @Parameter(description = "로그인 아이디")
            @PathVariable String nickName) {
        return ResponseEntity.ok(memberService.isNickNameExist(nickName));
    }

    @Operation(summary = "회원 가입 API", description = "신규 회원 가입을 한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "입력값이 잘못되었습니다.")
    })
    @PostMapping("/register")
    public ResponseEntity<Void> insert(@RequestBody MemberDTO memberDTO) {
        memberService.insert(memberDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberDTO> getMyInfo(Authentication authentication) {
       System.out.println("authentication: " + authentication);
        String loginId = authentication.getName();
        MemberDTO memberDTO = memberService.selectByLoginId(loginId);
        return ResponseEntity.ok(memberDTO);
    }

    @PutMapping("/me")
    public ResponseEntity<MemberDTO> updateMyInfo(@RequestBody MemberUpdateDTO dto, Authentication authentication) {
        String loginId = authentication.getName(); // 현재 로그인된 유저 아이디
        MemberDTO updatedMember = memberService.updateMyInformation(loginId, dto);
        return ResponseEntity.ok(updatedMember);
    }

}
