package com.end2end.ansimnuri.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "로그인 결과 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResultDTO {
    @Schema(description = "유저 id", example = "1", minimum = "1")
    private long id;
    @Schema(description = "토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
}
