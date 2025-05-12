package com.end2end.ansimnuri.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "수정 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberUpdateDTO {

    private String nickname;
    private String address;
    private String detailAddress;
}
