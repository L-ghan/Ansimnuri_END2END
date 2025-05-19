package com.end2end.ansimnuri.map.domain.entity;

import com.end2end.ansimnuri.map.dto.CctvDTO;
import com.end2end.ansimnuri.util.entity.Timestamp;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@SequenceGenerator(
        name = "cctvSequenceGenerator",
        sequenceName = "CCTV_ID_SEQ",
        allocationSize = 1
)
@Table(name = "CCTV")
@Entity
public class Cctv {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cctvSequenceGenerator")
    private Long id;
    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;
    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;
    @Column(name = "INSTALL_DATE")
    private LocalDate installDate;
    @Column(name="CAMERA_COUNT", nullable = false)
    private Integer cameraCount;
    @Column(name = "ADDRESS")
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RISK_RATE_ID")
    private RiskRate riskRate;

    public static Cctv of(CctvDTO dto) {
        return Cctv.builder()
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .installDate(dto.getInstallDate())
                .cameraCount(dto.getCameraCount())
                .address(dto.getAddress())
                .build();
    }
}
