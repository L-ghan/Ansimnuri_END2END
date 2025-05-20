package com.end2end.ansimnuri.map.dto;

import com.end2end.ansimnuri.map.domain.entity.StreetLight;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "가로등 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StreetLightDTO {
    @Schema(description = "id", example = "1", minimum = "1")
    private long id;
    @Schema(description = "관리 번호", example = "강남터미널고가-01")
    private String managementNo;
    @Schema(description = "주소", example = "서울 서초구 반포동 128-3")
    private String address;
    @Schema(description = "위도", example = "126.927112885")
    private double latitude;
    @Schema(description = "경도", example = "37.490042567")
    private double longitude;

    public static StreetLightDTO of(StreetLight streetLight) {
        return StreetLightDTO.builder()
                .id(streetLight.getId())
                .managementNo(streetLight.getManagementNo())
                .address(streetLight.getAddress())
                .latitude(streetLight.getLatitude())
                .longitude(streetLight.getLongitude())
                .build();
    }
}
