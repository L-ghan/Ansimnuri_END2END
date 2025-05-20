package com.end2end.ansimnuri.map.domain.entity;

import com.end2end.ansimnuri.map.dto.StreetLightDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@SequenceGenerator(
        sequenceName = "lightSequenceGenerator",
        name = "STREET_LIGHT_ID_SEQ",
        allocationSize = 1
)
@Table(name = "STREET_LIGHT")
@Entity
public class StreetLight {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lightSequenceGenerator")
    private Long id;
    @Column(name = "MANAGEMENT_NO", nullable = false)
    private String managementNo;
    @Column(name = "ADDRESS", nullable = false)
    private String address;
    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;
    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;

    @OneToMany(mappedBy = "streetLight")
    private List<RiskRate> riskRateList;

    public static StreetLight of(StreetLightDTO dto) {
        return StreetLight.builder()
                .managementNo(dto.getManagementNo())
                .address(dto.getAddress())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
    }
}
