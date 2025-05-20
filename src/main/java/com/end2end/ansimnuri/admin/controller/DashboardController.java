package com.end2end.ansimnuri.admin.controller;

import com.end2end.ansimnuri.admin.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/top-addresses")
    public ResponseEntity<List<Map<String, Object>>> getTopAddresses() {
        List<Map<String, Object>> topAddresses = dashboardService.getTop5Addresses();
        return ResponseEntity.ok(topAddresses);
    }

    @GetMapping("/keywords")
    public ResponseEntity<List<Map<String, Object>>> getKeywordStats() {
        System.out.println("키워드 통계 요청 들어옴");
        List<Map<String, Object>> stats = dashboardService.getKeywordStats();
        return ResponseEntity.ok(stats);
    }

}
