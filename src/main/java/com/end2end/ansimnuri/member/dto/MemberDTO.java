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
    private String loginId;
    private String password;
    private String nickname;
    private String email;
    private String postcode;
    private String address;
    private String detailAddress;
    private Roles role;
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
                .build();
    }
}
