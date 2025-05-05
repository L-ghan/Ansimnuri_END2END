package com.end2end.ansimnuri.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "유저 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @Schema(description = "유저 id", example = "1", minimum = "1")
    private long id;
}
