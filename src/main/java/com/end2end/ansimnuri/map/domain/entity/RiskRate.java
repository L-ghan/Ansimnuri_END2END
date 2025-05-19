package com.end2end.ansimnuri.map.domain.entity;

import com.end2end.ansimnuri.map.dto.RiskRateDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@SequenceGenerator(
        name="riskRateSequenceGenerator",
        sequenceName = "RISK_RATE_ID_SEQ",
        allocationSize = 1
)
@Table(name = "RISK_RATE")
@Entity
public class RiskRate {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "riskRateSequenceGenerator")
    private Long id;
    @Column(name = "RISK_RATE", nullable = false)
    private Integer riskRate;
    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;
    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;
    @Column(name = "ILLUMINANCE", nullable = false)
    private Double illuminance;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POLICE_ID", nullable = false)
    private Police police;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SEX_OFFENDER_ID", nullable = false)
    private SexOffender sexOffender;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STREET_LIGHT_ID", nullable = false)
    private StreetLight streetLight;

    @OneToMany(mappedBy = "riskRate", fetch = FetchType.LAZY)
    private List<Cctv> cctvList;

    public static RiskRate of(RiskRateDTO riskRateDTO) {
        return RiskRate.builder()
                .id(riskRateDTO.getId())
                .riskRate(riskRateDTO.getRiskRate())
                .latitude(riskRateDTO.getLatitude())
                .longitude(riskRateDTO.getLongitude())
                .build();
    }

    public void update(RiskRateDTO riskRateDTO) {
        this.riskRate = riskRateDTO.getRiskRate();
        this.latitude = riskRateDTO.getLatitude();
        this.longitude = riskRateDTO.getLongitude();
    }
}
