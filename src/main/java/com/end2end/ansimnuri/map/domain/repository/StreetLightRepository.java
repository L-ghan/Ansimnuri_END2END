package com.end2end.ansimnuri.map.domain.repository;

import com.end2end.ansimnuri.map.domain.entity.StreetLight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreetLightRepository extends JpaRepository<StreetLight, Long> {
}
