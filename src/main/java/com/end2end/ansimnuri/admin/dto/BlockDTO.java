package com.end2end.ansimnuri.admin.dto;

import com.end2end.ansimnuri.admin.domain.entity.Block;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlockDTO {
    private long id;
    private long memberId;
    private String reason;
    private LocalDateTime endDate;
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
