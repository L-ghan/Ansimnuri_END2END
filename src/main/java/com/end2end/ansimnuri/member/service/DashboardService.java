package com.end2end.ansimnuri.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class DashboardService {

    private final com.end2end.ansimnuri.member.repository.DashboardRepository dashboardRepository;

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
}
