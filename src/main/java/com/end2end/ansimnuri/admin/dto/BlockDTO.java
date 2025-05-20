package com.end2end.ansimnuri.admin.dto;

import com.end2end.ansimnuri.admin.domain.entity.Block;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "차단 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlockDTO {
    @Schema(description = "차단 id", example = "1", minimum = "1")
    private long id;
    @Schema(description = "유저 id", example = "1", minimum = "1")
    private long memberId;
    @Schema(description = "차단 사유", example = "욕성")
    private String reason;
    @Schema(description = "종료 일자", example = "2024-02-20 10:00:00", format = "date-time")
    private LocalDateTime endDate;
    @Schema(description = "차단 일자", example = "2024-02-01 10:00:00", format = "date-time")
    private LocalDateTime regDate;

    public static BlockDTO of(Block block) {
        return BlockDTO.builder()
                .id(block.getId())
                .memberId(block.getMember().getId())
                .reason(block.getReason())
                .endDate(block.getEndDate())
                .regDate(block.getRegDt())
                .build();
    }
}
