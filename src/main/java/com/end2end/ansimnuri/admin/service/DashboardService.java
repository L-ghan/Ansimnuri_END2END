package com.end2end.ansimnuri.admin.service;

import com.end2end.ansimnuri.admin.domain.repository.DashboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class DashboardService {

    private final DashboardRepository dashboardRepository;

    public List<Map<String, Object>> getTop5Addresses() {
        Pageable top5 = PageRequest.of(0, 5);
        List<Object[]> results = dashboardRepository.findTopAddresses(top5);

        List<Map<String, Object>> topAddresses = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("address", row[0]);
            map.put("count", row[1]);
            topAddresses.add(map);
        }
        return topAddresses;
    }
    public List<Map<String, Object>> getKeywordStats() {
        List<Object[]> results = dashboardRepository.findKeywordStats();

        List<Map<String, Object>> keywordStats = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("keyword", row[0]);  // question
            map.put("count", row[1]);    // count
            keywordStats.add(map);
        }
        return keywordStats;
    }

}
