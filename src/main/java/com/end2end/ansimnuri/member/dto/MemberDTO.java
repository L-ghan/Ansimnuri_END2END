package com.end2end.ansimnuri.member.dto;

import com.end2end.ansimnuri.member.domain.entity.Member;
import com.end2end.ansimnuri.util.enums.Roles;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "유저 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {
    @Schema(description = "유저 id", example = "1", minimum = "1")
    private long id;
    @Schema(description = "로그인 아이디", example = "login1234")
    private String loginId;
    @Schema(description = "비밀번호", example = "<PASSWORD>")
    private String password;
    @Schema(description = "카카오아이디", example = "kakaoId")
    private String kakaoId;
    @Schema(description = "닉네임", example = "홍길동")
    private String nickname;
    @Schema(description = "이메일", example = "hgd1234@gmail.com")
    private String email;
    @Schema(description = "우편번호", example = "12345")
    private String postcode;
    @Schema(description = "주소", example = "oo시 xx동 ㅁㅁ길")
    private String address;
    @Schema(description = "상세 주소", example = "1111동 111호")
    private String detailAddress;
    @Schema(description = "권한", example = "ROLE_USER")
    private Roles role;
    @Schema(description = "가입 일자", example = "2024-02-20 10:00:00", format = "date-time")
    private LocalDateTime regDate;

    public static MemberDTO of(Member member) {
        return MemberDTO.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .postcode(member.getPostcode())
                .address(member.getAddress())
                .detailAddress(member.getDetailAddress())
                .role(member.getRole())
                .regDate(member.getRegDt())
                .kakaoId(member.getKakaoId())
                .build();
    }
}
