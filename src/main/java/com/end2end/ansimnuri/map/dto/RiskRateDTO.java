package com.end2end.ansimnuri.map.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "위험도 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RiskRateDTO {
    @Schema(description = "id", example = "1", minimum = "1")
    private long id;
}
