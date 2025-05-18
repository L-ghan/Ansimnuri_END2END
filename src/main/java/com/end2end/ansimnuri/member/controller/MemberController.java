package com.end2end.ansimnuri.member.controller;

import com.end2end.ansimnuri.member.dto.LoginDTO;
import com.end2end.ansimnuri.member.dto.LoginResultDTO;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Map;

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
    public ResponseEntity<LoginResultDTO> login(@RequestBody LoginDTO dto) {
        System.out.println("dto: " + dto);
        return ResponseEntity.ok(memberService.login(dto));
    }
    @PostMapping("/register")
    public ResponseEntity<MemberDTO> register(@RequestBody MemberDTO dto) {
        memberService.register(dto);
        return ResponseEntity.ok(dto);
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
        System.out.println("nickName: " + nickName);
        return ResponseEntity.ok(memberService.isNickNameExist(nickName));
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

    @PostMapping("/checkEmail")
    public ResponseEntity<Boolean> checkEmail(@RequestBody MemberDTO dto) {

        boolean result = memberService.checkEmail(dto.getEmail());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/checkPw")
    public ResponseEntity<Boolean> checkPw(@RequestBody LoginDTO dto,Authentication authentication) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String rawPassword = dto.getPassword();

        String loginId =  authentication.getName();
      String pw = memberService.getPw(loginId);

        boolean matches = passwordEncoder.matches(rawPassword, pw);
        System.out.println("비밀번호 일치 여부: " + matches);
        return ResponseEntity.ok(matches);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody LoginDTO dto, Authentication authentication) {
String pw = dto.getPassword();
String loginId = authentication.getName();
        memberService.changePassword(loginId, pw);
        return ResponseEntity.ok("비밀번호 변경 성공");
    }
    @PostMapping("/id/changePassword")
    public ResponseEntity<?> loginIdBychangePassword(@RequestBody LoginDTO dto) {
        String pw = dto.getPassword();
        String loginId = dto.getLoginId();
        memberService.changePassword(loginId, pw);
        return ResponseEntity.ok("비밀번호 변경 성공");
    }

    @PostMapping("/email/changeLoginId")
    public ResponseEntity<?> changeLoginIdByemail(@RequestBody MemberDTO dto) {
        String loginId = dto.getLoginId() ;
        String email = dto.getEmail();
        memberService.changeLoginIdByemail(email, loginId);
        return ResponseEntity.ok("아이디 변경 성공");
    }
    @DeleteMapping("/delete/{loginId}")
    public ResponseEntity<Void>deleteMember(@PathVariable String loginId, Authentication authentication){

       if(!authentication.getName().equals(loginId)){
           return ResponseEntity.badRequest().build();
       }//혹시모를 상황을 대비해서 인증된 회원이랑 요청한 아이디값이 다른경우
       memberService.deleteByLoginId(loginId);
        return ResponseEntity.ok().build();
    }


}
