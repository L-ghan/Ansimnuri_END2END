package com.end2end.ansimnuri.map.dao;

import com.end2end.ansimnuri.map.dto.RiskRateDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class RiskRateDAO {
    private final SqlSession mybatis;

    public void insrtAll(List<RiskRateDTO> riskRateDTOList) {
        mybatis.insert("riskRate.insertAll", riskRateDTOList);
    }

    public void deleteAll() {
        mybatis.delete("riskRate.deleteAll");
    }
}
