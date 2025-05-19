package com.end2end.ansimnuri.map.dto;

import com.end2end.ansimnuri.map.domain.entity.StreetLight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StreetLightDTO {
    private long id;
    private String managementNo;
    private String address;
    private double latitude;
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
