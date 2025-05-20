package com.end2end.ansimnuri.map.domain.repository;

import com.end2end.ansimnuri.map.domain.entity.StreetLight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StreetLightRepository extends JpaRepository<StreetLight, Long> {
    List<StreetLight> findAllByOrderByLatitudeAscLongitudeAsc();
}
