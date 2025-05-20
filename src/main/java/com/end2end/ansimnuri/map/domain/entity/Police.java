package com.end2end.ansimnuri.map.domain.entity;

import com.end2end.ansimnuri.map.dto.PoliceDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@SequenceGenerator(
        name = "policeSequenceGenerator",
        sequenceName = "POLICE_ID_SEQ",
        allocationSize = 1
)
@Table(name = "POLICE")
@Entity
public class Police {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "policeSequenceGenerator")
    private Long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "ADDRESS", nullable = false)
    private String address;
    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;
    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;
    @Column(name = "TYPE", nullable = false)
    private String type;

    @OneToMany(mappedBy = "police")
    private List<RiskRate> riskRateList;

    public static Police of(PoliceDTO dto) {
        return Police.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .type(dto.getType())
                .build();
    }
}
