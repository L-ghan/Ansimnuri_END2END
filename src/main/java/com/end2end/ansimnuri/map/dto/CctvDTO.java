package com.end2end.ansimnuri.map.dto;

import com.end2end.ansimnuri.map.domain.entity.Cctv;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema(description = "CCTV DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CctvDTO {
    @Schema(description = "id", example = "1", minimum = "1")
    private long id;
    @Schema(description = "위도", example = "126.927112885")
    private double latitude;
    @Schema(description = "경도", example = "37.490042567")
    private double longitude;
    @Schema(description = "주소", example = "서울특별시 관악구 봉천로 227 (봉천동)")
    private String address;
    @Schema(description = "카메라 대수", example =  "3", minimum = "1")
    private int cameraCount;
    @Schema(description = "설치 일자", example = "2024-10-01")
    private LocalDate installDate;

    public static CctvDTO of(Cctv cctv) {
        return CctvDTO.builder()
                .id(cctv.getId())
                .latitude(cctv.getLatitude())
                .longitude(cctv.getLongitude())
                .address(cctv.getAddress())
                .cameraCount(cctv.getCameraCount())
                .installDate(cctv.getInstallDate())
                .build();
    }
}
