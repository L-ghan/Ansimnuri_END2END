package com.end2end.ansimnuri.map.dto;

import com.end2end.ansimnuri.map.domain.entity.Police;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PoliceDTO {
    private long id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String type;

    public static PoliceDTO of(Police police) {
        return PoliceDTO.builder()
                .id(police.getId())
                .name(police.getName())
                .address(police.getAddress())
                .latitude(police.getLatitude())
                .longitude(police.getLongitude())
                .type(police.getType())
                .build();
    }
}
