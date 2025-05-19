package com.end2end.ansimnuri.map.domain.entity;

import com.end2end.ansimnuri.map.dto.SexOffenderDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@SequenceGenerator(
        sequenceName = "sexSequenceGenerator",
        name = "SEX_OFFENDER_ID_SEQ",
        allocationSize = 1
)
@Table(name = "SEX_OFFENDER")
@Entity
public class SexOffender {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sexSequenceGenerator")
    private Long id;
    @Column(name = "ADDRESS", nullable = false)
    private String address;
    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;
    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;
    @Column(name = "ROAD_ZIP", nullable = false)
    private Integer roadZip;

    public static SexOffender of(SexOffenderDTO dto) {
        return SexOffender.builder()
                .address(dto.getAddress())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .roadZip(dto.getRoadZip())
                .build();
    }
}
