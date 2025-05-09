package com.end2end.ansimnuri.map.controller;

import com.end2end.ansimnuri.map.service.RiskRateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "지도 관련 API", description = "맵 데이터에 관련한 기능을 가진 API")
@RequiredArgsConstructor
@RequestMapping("/api/map")
@RestController
public class MapDataController {
    private final RiskRateService riskRateService;
}
