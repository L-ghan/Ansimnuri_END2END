package com.end2end.ansimnuri.map.dto;

import com.end2end.ansimnuri.map.domain.entity.SexOffender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "성범죄자 위치 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SexOffenderDTO {
    @Schema(description = "id", example = "1", minimum = "1")
    private long id;
    @Schema(description = "주소", example = "서울특별시 강북구 xx로")
    private String address;
    @Schema(description = "위도", example = "126.927112885")
    private double latitude;
    @Schema(description = "경도", example = "37.490042567")
    private double longitude;
    @Schema(description = "도로주소", example = "1000")
    private int roadZip;

    public static SexOffenderDTO of(SexOffender sexOffender) {
        return SexOffenderDTO.builder()
                .id(sexOffender.getId())
                .address(sexOffender.getAddress())
                .latitude(sexOffender.getLatitude())
                .longitude(sexOffender.getLongitude())
                .roadZip(sexOffender.getRoadZip())
                .build();
    }
}
