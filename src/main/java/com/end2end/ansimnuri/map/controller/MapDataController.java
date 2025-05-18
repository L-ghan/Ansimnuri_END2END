package com.end2end.ansimnuri.map.controller;

import com.end2end.ansimnuri.map.dto.CctvDTO;
import com.end2end.ansimnuri.map.dto.PoliceDTO;
import com.end2end.ansimnuri.map.dto.SearchHistoryDTO;
import com.end2end.ansimnuri.map.service.CctvService;
import com.end2end.ansimnuri.map.service.PoliceService;
import com.end2end.ansimnuri.map.service.RiskRateService;
import com.end2end.ansimnuri.map.service.SearchHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "지도 관련 API", description = "맵 데이터에 관련한 기능을 가진 API")
@RequiredArgsConstructor
@RequestMapping("/api/map")
@RestController
public class MapDataController {
    private final RiskRateService riskRateService;
    private final SearchHistoryService searchHistoryService;
    private final PoliceService policeService;
    private final CctvService cctvService;

    @Operation(summary = "유저의 검색기록 조회 api", description = "해당 id를 가진 유저의 검색 결과를 모두 조회한다.")
    @GetMapping("/search/history/{memberId}")
    public ResponseEntity<List<SearchHistoryDTO>> selectByMemberId(
            @Parameter(description = "유저 id")
            @PathVariable long memberId) {
        return ResponseEntity.ok(searchHistoryService.selectByMemberId(memberId));
    }

    @Operation(summary = "검색 기록 삭제 api", description = "해당 id의 검색 기록을 삭제한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없습니다.")
    })
    @DeleteMapping("/search/history/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "검색 기록 id")
            @PathVariable long id) {
        searchHistoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "모든 경찰서 조회 API", description = "서울시의 모든 경찰서를 조회한다.")
    @ApiResponse(responseCode = "200", description = "정상 작동입니다.")
    @GetMapping("/police")
    public ResponseEntity<List<PoliceDTO>> selectAllPolice() {
        return ResponseEntity.ok(policeService.selectAll());
    }

    @Operation(summary = "경찰서 주소 검색 API", description = "해당 내용을 포함한 주소를 가진 모든 경찰서를 조회한다.")
    @ApiResponse(responseCode = "200", description = "정상 작동입니다.")
    @GetMapping("/police/address/{searchKey}")
    public ResponseEntity<List<PoliceDTO>> selectByTAddressLike(
            @Parameter(description = "검색 내용")
            @PathVariable String searchKey) {
        return ResponseEntity.ok(policeService.selectByAddressLike(searchKey));
    }

    @Operation(summary = "겅찰서 상세 조회 API", description = "해당 id를 가진 경찰서를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "해당 id를 가진 경찰서는 존재하지 않습니다.")
    })
    @GetMapping("/police/{id}")
    public PoliceDTO selectById(
            @Parameter(description = "경찰서 id")
            @PathVariable long id) {
        return policeService.selectById(id);
    }

    @GetMapping("/cctv")
    public ResponseEntity<List<CctvDTO>> selectAllCctv() {
        return ResponseEntity.ok(cctvService.selectAll());
    }

    @GetMapping("/test")
    public void test() {
        cctvService.insert();
    }
}
