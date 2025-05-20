package com.end2end.ansimnuri.map.domain.repository;

import com.end2end.ansimnuri.map.domain.entity.Cctv;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CctvRepository extends JpaRepository<Cctv, Long> {
    List<Cctv> findByLatitudeBetweenAndLongitudeBetween(double latitude1, double latitude2, double longitude1, double longitude2);
    List<Cctv> findAllByOrderByLatitudeAscLongitudeAsc();
}
