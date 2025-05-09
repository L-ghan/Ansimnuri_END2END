package com.end2end.ansimnuri.map.service;

import com.end2end.ansimnuri.map.dao.RiskRateDAO;
import com.end2end.ansimnuri.map.domain.repository.RiskRateRepository;
import com.end2end.ansimnuri.map.dto.RiskRateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RiskRateServiceImpl implements RiskRateService {
    private final RiskRateDAO riskRateDAO;
    private final RiskRateRepository riskRateRepository;

    @Scheduled(cron = "0 0 0 1 1 *")
    @Transactional
    @Override
    public void insertAll() {
        riskRateRepository.deleteAll();

        List<RiskRateDTO> riskRateDTOList = new ArrayList<>();
        riskRateDAO.insrtAll(riskRateDTOList);
    }
}
