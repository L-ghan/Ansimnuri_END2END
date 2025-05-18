package com.end2end.ansimnuri.map.dto;

import com.end2end.ansimnuri.map.domain.entity.Cctv;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CctvDTO {
    private long id;
    private double latitude;
    private double longitude;
    private String address;
    private int cameraCount;
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
