package com.end2end.ansimnuri.map.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
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
}
