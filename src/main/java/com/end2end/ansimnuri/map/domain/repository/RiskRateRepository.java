package com.end2end.ansimnuri.map.domain.repository;

import com.end2end.ansimnuri.map.domain.entity.RiskRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RiskRateRepository extends JpaRepository<RiskRate, Long> {
    @Modifying
    @Query("DELETE FROM RiskRate ")
    void deleteAll();
}
