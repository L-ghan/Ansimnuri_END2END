package com.end2end.ansimnuri.map.service;

import com.end2end.ansimnuri.map.dao.RiskRateDAO;
import com.end2end.ansimnuri.map.domain.repository.CctvRepository;
import com.end2end.ansimnuri.map.domain.repository.PoliceRepository;
import com.end2end.ansimnuri.map.domain.repository.RiskRateRepository;
import com.end2end.ansimnuri.map.dto.CctvDTO;
import com.end2end.ansimnuri.map.dto.PoliceDTO;
import com.end2end.ansimnuri.map.dto.RiskRateDTO;
import com.end2end.ansimnuri.util.GeoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RiskRateServiceImpl implements RiskRateService {
    private final RiskRateDAO riskRateDAO;
    private final RiskRateRepository riskRateRepository;
    private final CctvRepository cctvRepository;
    private final PoliceRepository policeRepository;

    @Scheduled(cron = "0 0 0 1 1 *")
    @Transactional
    @Override
    public void insertAll() {
        riskRateRepository.deleteAll();

        List<RiskRateDTO> riskRateDTOList = new ArrayList<>();
        riskRateDAO.insrtAll(riskRateDTOList);
    }

    @Override
    public int getRiskRate(double latitude, double longitude) {
        GeoUtil.Rectangle cctvPoints = new GeoUtil.Rectangle(0.2, latitude, longitude);

        List<CctvDTO> cctvList = cctvRepository.findByLatitudeBetweenAndLongitudeBetween(
                cctvPoints.getSouth(), cctvPoints.getNorth(), cctvPoints.getWest(), cctvPoints.getEast()).stream()
                .map(CctvDTO::of)
                .toList();

        PoliceDTO nearPolice = policeRepository.findAll().stream()
                .min(Comparator.comparingDouble(police ->
                        GeoUtil.getDistance(latitude, longitude, police.getLatitude(), police.getLongitude())))
                .map(PoliceDTO::of)
                .orElse(null);

        return 0;
    }
}
