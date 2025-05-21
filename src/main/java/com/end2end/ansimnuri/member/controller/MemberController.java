package com.end2end.ansimnuri.member.controller;

import com.end2end.ansimnuri.member.dto.LoginDTO;
import com.end2end.ansimnuri.member.dto.LoginResultDTO;
import com.end2end.ansimnuri.member.dto.MemberDTO;
import com.end2end.ansimnuri.member.dto.MemberUpdateDTO;
import com.end2end.ansimnuri.member.service.MemberService;
import com.end2end.ansimnuri.util.exception.UnAuthenticationException;
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
    public ResponseEntity<LoginResultDTO> login(@RequestBody LoginDTO dto) {
        System.out.println("dto: " + dto);
        return ResponseEntity.ok(memberService.login(dto));
    }
    @PostMapping("/register")
    public ResponseEntity<MemberDTO> register(@RequestBody MemberDTO dto) {
        memberService.register(dto);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/kakaoSignup")
public ResponseEntity<MemberDTO> kakaoSignup(@RequestBody MemberDTO dto){
      String nickname =  dto.getNickname();
        String kakaoID = dto.getKakaoId();
        memberService.registerOAuthIfNeeded(nickname, kakaoID);
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

    @Operation(summary = "내 정보 조회 API", description = "로그인한 계정의 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "해당 유저는 존재하지 않습니다."),
            @ApiResponse(responseCode = "401", description = "해당 서비스는 로그인이 필요합니다.")
    })
    @GetMapping("/me")
    public ResponseEntity<MemberDTO> getMyInfo(Authentication authentication) {
        System.out.println("authentication: " + authentication);
        String loginId = authentication.getName();
        MemberDTO memberDTO = memberService.selectByLoginId(loginId);
        return ResponseEntity.ok(memberDTO);
    }

    @Operation(summary = "내 정보 수정 API", description = "로그인한 계정의 정보를 수정한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "입력 데이터가 잘못되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 유저는 존재하지 않습니다."),
            @ApiResponse(responseCode = "401", description = "해당 서비스는 로그인이 필요합니다.")
    })
    @PutMapping("/me")
    public ResponseEntity<MemberDTO> updateMyInfo(@RequestBody MemberUpdateDTO dto, Authentication authentication) {
        String loginId = authentication.getName(); // 현재 로그인된 유저 아이디
        MemberDTO updatedMember = memberService.updateMyInformation(loginId, dto);
        return ResponseEntity.ok(updatedMember);
    }

    @Operation(summary = "이메일 중복 확인 API", description = "입력받은 데이터의 이메일이 있는지 확인한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "입력 데이터가 잘못되었습니다.")
    })
    @PostMapping("/checkEmail")
    public ResponseEntity<Boolean> checkEmail(@RequestBody MemberDTO dto) {
        return ResponseEntity.ok(memberService.checkEmail(dto.getEmail()));
    }

    @Operation(summary = "비밀번호 일치 확인 API", description = "입력받은 비밀번호가 현재 계정의 비밀번호와 같은지 확인한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "해당 아이디를 가진 유저가 없습니다."),
            @ApiResponse(responseCode = "401", description = "해당 서비스는 로그인이 필요합니다.")
    })
    @PostMapping("/checkPw")
    public ResponseEntity<Boolean> checkPw(@RequestBody LoginDTO dto,Authentication authentication) {
        return ResponseEntity.ok(memberService.checkPassword(authentication.getName(), dto.getPassword()));
    }

    @Operation(summary = "비밀번호 변경 API", description = "입력받은 비밀번호로 변경한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "해당 아이디를 가진 유저가 없습니다."),
            @ApiResponse(responseCode = "401", description = "해당 서비스는 로그인이 필요합니다.")
    })
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody LoginDTO dto, Authentication authentication) {
        String pw = dto.getPassword();
        String loginId = authentication.getName();
        memberService.changePassword(loginId, pw);
        return ResponseEntity.ok("비밀번호 변경 성공");
    }

    @Operation(summary = "비밀번호 찾기 API", description = "비밀번호를 잃어버렸을 때 로그인 하지 않고 새로 변경한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "해당 아이디를 가진 유저는 존재하지 않습니다.")
    })
    @PostMapping("/id/changePassword")
    public ResponseEntity<String> loginIdBychangePassword(@RequestBody LoginDTO dto) {
        String pw = dto.getPassword();
        String loginId = dto.getLoginId();
        memberService.changePassword(loginId, pw);
        return ResponseEntity.ok("비밀번호 변경 성공");
    }

    @Operation(summary = "아이디 찾기 API", description = "이메일이 같은지 확인 후 아이디를 변경한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "해당 이메일을 가진 유저가 없습니다.")
    })
    @PostMapping("/email/changeLoginId")
    public ResponseEntity<String> changeLoginIdByemail(@RequestBody MemberDTO dto) {
        String loginId = dto.getLoginId() ;
        String email = dto.getEmail();
        memberService.changeLoginIdByemail(email, loginId);
        return ResponseEntity.ok("아이디 변경 성공");
    }

    @Operation(summary = "유저 삭제 API", description = "해당 로그인 아이디를 가진 유저를 삭제한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "해당 로그인 아이디를 가진 유저가 없습니다."),
            @ApiResponse(responseCode = "401", description = "해당 서비스는 로그인이 필요합니다."),
            @ApiResponse(responseCode = "401", description = "해당 서비스는 로그인한 사람만 사용 가능합니다.")
    })
    @DeleteMapping("/delete/{loginId}")
    public ResponseEntity<Void>deleteMember(@PathVariable String loginId, Authentication authentication){
       if(!authentication.getName().equals(loginId)){
           throw new UnAuthenticationException();
       }//혹시모를 상황을 대비해서 인증된 회원이랑 요청한 아이디값이 다른경우
       memberService.deleteByLoginId(loginId);
        return ResponseEntity.ok().build();
    }
}
