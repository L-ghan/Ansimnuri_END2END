package com.end2end.ansimnuri.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "유저 수정 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberUpdateDTO {
    @Schema(description = "닉네임", example = "홍길동")
    private String nickname;
    @Schema(description = "주소", example = "oo시 xx동 ㅁㅁ길")
    private String address;
    @Schema(description = "상세 주소", example = "1111동 111호")
    private String detailAddress;
}
