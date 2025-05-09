package com.end2end.ansimnuri.map.controller;

import com.end2end.ansimnuri.map.dto.SearchHistoryDTO;
import com.end2end.ansimnuri.map.service.PoliceService;
import com.end2end.ansimnuri.map.service.RiskRateService;
import com.end2end.ansimnuri.map.service.SearchHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "지도 관련 API", description = "맵 데이터에 관련한 기능을 가진 API")
@RequiredArgsConstructor
@RequestMapping("/api/map")
@RestController
public class MapDataController {
    private final RiskRateService riskRateService;
    private final SearchHistoryService searchHistoryService;
    private final PoliceService policeService;

    @Operation(summary = "유저의 검색기록 조회 api", description = "해당 id를 가진 유저의 검색 결과를 모두 조회한다.")
    @GetMapping("/search/history/{memberId}")
    public ResponseEntity<List<SearchHistoryDTO>> selectByMemberId(
            @Parameter(description = "유저 id")
            @PathVariable long memberId) {
        return ResponseEntity.ok(searchHistoryService.selectByMemberId(memberId));
    }
}
