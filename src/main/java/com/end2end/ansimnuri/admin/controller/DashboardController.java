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
}
