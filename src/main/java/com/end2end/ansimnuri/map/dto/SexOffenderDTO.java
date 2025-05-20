package com.end2end.ansimnuri.map.dto;

import com.end2end.ansimnuri.map.domain.entity.SexOffender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SexOffenderDTO {
    private long id;
    private String address;
    private double latitude;
    private double longitude;
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
