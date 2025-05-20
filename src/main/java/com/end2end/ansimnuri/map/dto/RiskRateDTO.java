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
    @Schema(description = "위험도", example = "1", minimum = "1", maximum = "5")
    private int riskRate;
    @Schema(description = "위도", example = "126.927112885")
    private double latitude;
    @Schema(description = "경도", example = "37.490042567")
    private double longitude;
    @Schema(description = "가까운 경찰서 id", example = "1", minimum = "1")
    private long policeId;
    @Schema(description = "가까운 성폭행범 위치 id", example = "1", minimum = "1")
    private long sexOffenderId;
    @Schema(description = "가까운 가로등 위치 id", example = "1", minimum = "1")
    private long streetLightId;
}
