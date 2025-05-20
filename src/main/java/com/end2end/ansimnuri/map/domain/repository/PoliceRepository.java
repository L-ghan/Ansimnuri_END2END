package com.end2end.ansimnuri.map.domain.repository;

import com.end2end.ansimnuri.map.domain.entity.Police;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PoliceRepository extends JpaRepository<Police, Long> {
    List<Police> findByAddressContaining(String searchKey);
    List<Police> findByType(String type);
    List<Police> findAllByOrderByLatitudeAscLongitudeAsc();
}
