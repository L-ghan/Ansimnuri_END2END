package com.end2end.ansimnuri.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "로그인 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTO {
    @Schema(description = "로그인 아이디", example = "login1234")
    private String loginId;
    @Schema(description = "비밀번호", example = "<PASSWORD>")
    private String password;
}
